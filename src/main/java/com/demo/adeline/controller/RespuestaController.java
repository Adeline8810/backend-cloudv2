package com.demo.adeline.controller;

import com.demo.adeline.model.Respuesta;
import com.demo.adeline.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RestController
@RequestMapping("/api/respuestas")
//@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    
    @CrossOrigin(origins = "*")
    @GetMapping("/traducir")
    public ResponseEntity<Map<String, String>> traducir(@RequestParam String texto, @RequestParam String target) {
        String url = "https://libretranslate.de/translate";
        
        RestTemplate restTemplate = new RestTemplate();
        
        // 1. Creamos las cabeceras (Headers) indicando que enviamos un formulario
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("q", texto);
        body.put("source", "auto");
        body.put("target", target);
        body.put("format", "text");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

      

        try {
            // 3. Hacemos la petición POST
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            Map<String, String> resultado = new HashMap<>();
            
            if (response != null) {
                System.out.println("Respuesta: " + response);

                if (response.containsKey("translatedText")) {
                    resultado.put("traducido", response.get("translatedText").toString());
                } else if (response.containsKey("data")) {
                    Map data = (Map) response.get("data");
                    resultado.put("traducido", data.get("translatedText").toString());
                } else {
                    resultado.put("traducido", texto);
                }
            }
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            // Si falla, imprimimos el error en la consola de Railway para saber por qué
            System.out.println("Error en traducción: " + e.getMessage());
            Map<String, String> errorRes = new HashMap<>();
            errorRes.put("traducido", texto);
            return ResponseEntity.ok(errorRes);
        }
    }
    
    
}
