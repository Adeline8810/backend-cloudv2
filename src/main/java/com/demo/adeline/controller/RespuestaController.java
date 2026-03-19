package com.demo.adeline.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.demo.adeline.model.Respuesta;
import com.demo.adeline.model.RespuestaAmigoDTO;
import com.demo.adeline.repository.RespuestaRepository;
import com.demo.adeline.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/respuestas")
//@CrossOrigin(origins = "*")

public class RespuestaController {

    private final RespuestaRepository repo;
    private final UsuarioRepository usuarioRepo;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public RespuestaController(RespuestaRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo; // <--- AÑADE ESTO
    }

    @CrossOrigin(origins = "https://adeline8810.github.io")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFoto(@RequestParam("file") MultipartFile file,@RequestParam("usuarioId") Long usuarioId) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file");
        }

        String projectPath = System.getProperty("user.dir");
        File dir = new File(projectPath, uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = System.currentTimeMillis() + "-" +
                file.getOriginalFilename().replaceAll("\\s+", "_");

        File dest = new File(dir, filename);
        file.transferTo(dest);

        // Retornamos el path relativo para que Angular construya la URL
        String publicPath = "/uploads/" + filename;
        return ResponseEntity.ok(publicPath);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Respuesta> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }
    
    @PostMapping
    public ResponseEntity<List<Respuesta>> guardarOActualizarLista(@RequestBody List<Respuesta> nuevasRespuestas) {
        List<Respuesta> resultados = nuevasRespuestas.stream().map(nueva -> {
            List<Respuesta> existentes = repo.findByUsuarioIdAndPreguntaId(nueva.getUsuarioId(), nueva.getPreguntaId());
            
            if (!existentes.isEmpty()) {
                Respuesta existente = existentes.get(0);
                existente.setTexto(nueva.getTexto());
                existente.setFotoUrl(nueva.getFotoUrl());
                return repo.save(existente);
            } else {
                nueva.setId(null); 
                return repo.save(nueva);
            }
        }).toList();

        return ResponseEntity.ok(resultados);
    }
    
    @PostMapping("/traducir")
    public ResponseEntity<Map<String, String>> traducir(@RequestBody Map<String, String> bodyRequest) {
        String texto = bodyRequest.get("texto");
        String target = bodyRequest.get("target");
        String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=es&tl=" + target + "&dt=t&q=" + texto;
        
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> resultado = new HashMap<>();

        try {
            Object[] response = restTemplate.getForObject(url, Object[].class);
            if (response != null) {
                String traducido = ((List)((List)response[0]).get(0)).get(0).toString();
                resultado.put("traducido", traducido);
            } else {
                resultado.put("traducido", texto);
            }
        } catch (Exception e) {
            resultado.put("traducido", texto);
        }
        return ResponseEntity.ok(resultado);
    }

    /**
     * PASO 2: Trae las respuestas detalladas del amigo seleccionado
     * Se mantiene aquí porque usa RespuestaRepository y el DTO de respuestas
     */
    @GetMapping("/buscar-por-nombre")
    public List<RespuestaAmigoDTO> buscarPorNombre(@RequestParam String username) {
        return repo.buscarPorAmigo(username);
    }
}