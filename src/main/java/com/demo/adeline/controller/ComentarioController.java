package com.demo.adeline.controller;

import com.demo.adeline.model.Comentario;
import com.demo.adeline.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    // Guardar comentario o respuesta
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Comentario comentario) {
        try {
            Comentario nuevo = comentarioRepository.save(comentario);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar comentario: " + e.getMessage());
        }
    }

    // Listar comentarios de un video
    @GetMapping("/video/{videoId}")
    public ResponseEntity<?> listarPorVideo(@PathVariable Long videoId) {
        try {
            List<Comentario> lista = comentarioRepository.findByVideoIdOrderByFechaDesc(videoId);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener comentarios: " + e.getMessage());
        }
    }

    // Eliminar comentario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            comentarioRepository.deleteById(id);
            return ResponseEntity.ok().body("Eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar");
        }
    }
}