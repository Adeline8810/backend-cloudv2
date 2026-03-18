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
import com.demo.adeline.repository.RespuestaRepository;

@RestController
@RequestMapping("/api/respuestas")
@CrossOrigin(origins = "*")
public class RespuestaController {

    private final RespuestaRepository repo;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public RespuestaController(RespuestaRepository repo) {
        this.repo = repo;
    }

 

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFoto(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file");
        }

        // Ruta absoluta dentro del proyecto
        String projectPath = System.getProperty("user.dir");
        File dir = new File(projectPath, uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = System.currentTimeMillis() + "-" +
                file.getOriginalFilename().replaceAll("\\s+", "_");

        File dest = new File(dir, filename);

        file.transferTo(dest);

        // URL pública
        String publicPath = "/uploads/" + filename;

        return ResponseEntity.ok(publicPath);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Respuesta> obtenerPorUsuario(@PathVariable Long usuarioId) {
        // Esto llama al método que creamos en el Repository
        return repo.findByUsuarioId(usuarioId);
    }
    
    @PostMapping
    public ResponseEntity<List<Respuesta>> guardarOActualizarLista(@RequestBody List<Respuesta> nuevasRespuestas) {
        List<Respuesta> resultados = nuevasRespuestas.stream().map(nueva -> {
            // Buscamos todas las que coincidan (por si hay duplicados)
            List<Respuesta> existentes = repo.findByUsuarioIdAndPreguntaId(nueva.getUsuarioId(), nueva.getPreguntaId());
            
            if (!existentes.isEmpty()) {
                // SI EXISTEN: Usamos la primera que encontremos
                Respuesta existente = existentes.get(0);
                existente.setTexto(nueva.getTexto());
                existente.setFotoUrl(nueva.getFotoUrl());
                return repo.save(existente);
            } else {
                // NO EXISTE: Creamos registro nuevo desde cero
                // Limpiamos el ID por si Angular envió uno viejo que ya no existe
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

        // URL espejo gratuita que NO pide API Key ni falla como LibreTranslate.com
        String url = "https://libretranslate.de/translate";
        
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> resultado = new HashMap<>();

        // Datos para el traductor
        Map<String, String> libreBody = new HashMap<>();
        libreBody.put("q", texto);
        libreBody.put("source", "es");
        libreBody.put("target", target);
        libreBody.put("format", "text");
        libreBody.put("api_key", "");

        try {
            // Hacemos la petición al servidor de traducción
            Map<String, Object> response = restTemplate.postForObject(url, libreBody, Map.class);
            
            if (response != null && response.containsKey("translatedText")) {
                resultado.put("traducido", response.get("translatedText").toString());
            } else {
                resultado.put("traducido", texto); 
            }
        } catch (Exception e) {
            // Si el servidor de traducción falla, devolvemos el texto original para no dar error 500
            resultado.put("traducido", texto);
        }

        return ResponseEntity.ok(resultado);
    }
}
