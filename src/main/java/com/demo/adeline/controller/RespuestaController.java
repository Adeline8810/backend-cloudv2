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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.adeline.model.Respuesta;
import com.demo.adeline.repository.RespuestaRepository;

import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<Map<String, String>>> traducir(
            @RequestParam String texto,
            @RequestParam String target) {

        String urlBase = "https://translate.astian.org";

        Map<String, String> resultado = new HashMap<>();

        Map<String, String> body = new HashMap<>();
        body.put("q", texto);
        body.put("source", "auto");
        body.put("target", target);
        body.put("format", "text");

        WebClient client = WebClient.create(urlBase);

        return client.post()
                .uri("/translate")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    // 🔹 Procesamos respuesta
                    if (response != null && response.get("translatedText") != null) {
                        resultado.put("traducido", response.get("translatedText").toString());
                    } else {
                        resultado.put("traducido", "ERROR: respuesta vacía");
                    }
                    return ResponseEntity.ok(resultado);
                })
                .onErrorResume(e -> {
                    // 🔹 Captura de errores de red/DNS/servicio
                    System.out.println("ERROR REAL:");
                    e.printStackTrace();

                    resultado.put("traducido", "ERROR: servicio no disponible");
                    return Mono.just(ResponseEntity.ok(resultado));
                });
    }
    
}
