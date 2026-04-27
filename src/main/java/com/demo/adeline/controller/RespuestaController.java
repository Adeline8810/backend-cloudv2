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

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

	@RestController
	@RequestMapping("/api/respuestas")
public class RespuestaController {

    private final RespuestaRepository repo;
    private final Cloudinary cloudinary;
    // 1. ELIMINAMOS la referencia a UsuarioRepository si no la vas a usar, 
    // para que Spring no busque un frijol (bean) que no existe.

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    // 2. CONSTRUCTOR LIMPIO: Solo lo que realmente inyectas.
    public RespuestaController(RespuestaRepository repo,Cloudinary cloudinary) {
        this.repo = repo;
        this.cloudinary = cloudinary;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFoto(@RequestParam("file") MultipartFile file, @RequestParam("usuarioId") Long usuarioId) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file");
        }

        // SUBIDA DIRECTA A CLOUDINARY
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", "perfiles",
            "resource_type", "image"
        ));

        // Retornamos la URL segura que nos da Cloudinary
        String urlCloudinary = uploadResult.get("secure_url").toString();
        return ResponseEntity.ok(urlCloudinary);
    }
    
    // 3. CAMBIO IMPORTANTE: Agregamos un try-catch aquí para que no "explote" el servidor
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Respuesta> lista = repo.findByUsuarioId(usuarioId);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            // Esto nos dirá en los logs de Railway exactamente qué falló
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<List<Respuesta>> guardarOActualizarLista(@RequestBody List<Respuesta> nuevasRespuestas) {
        List<Respuesta> resultados = nuevasRespuestas.stream().map(nueva -> {
            // Asegúrate de que este método exista tal cual en tu RespuestaRepository
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
    
    // El resto del código (traducir y buscar-por-nombre) se queda igual.
    @PostMapping("/traducir")
    public ResponseEntity<Map<String, String>> traducir(@RequestBody Map<String, String> bodyRequest) {
        String texto = bodyRequest.get("texto");
        String target = bodyRequest.get("target");

        // 🚀 Usamos el motor de Google (vía script gratuito)
        String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=es&tl=" + target + "&dt=t&q=" + texto;
        
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> resultado = new HashMap<>();

        try {
            // Google devuelve un Array extraño, así que lo recibimos como Object
            Object[] response = restTemplate.getForObject(url, Object[].class);
            
            // Extraemos la traducción del formato de Google
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

    @GetMapping("/buscar-por-nombre")
    public List<RespuestaAmigoDTO> buscarPorNombre(@RequestParam String username) {
        return repo.buscarPorAmigo(username);
    }
}