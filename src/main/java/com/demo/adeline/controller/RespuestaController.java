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
            return repo.findByUsuarioIdAndPreguntaId(nueva.getUsuarioId(), nueva.getPreguntaId())
                .map(existente -> {
                    existente.setTexto(nueva.getTexto());
                    existente.setFotoUrl(nueva.getFotoUrl());
                    return repo.save(existente);
                })
                .orElseGet(() -> repo.save(nueva));
        }).toList(); // <--- Cambia esto aquí, es mucho más simple para Java 21

        return ResponseEntity.ok(resultados);
    }
    
    
    
}
