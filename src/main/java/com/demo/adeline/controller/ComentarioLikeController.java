package com.demo.adeline.controller;

import com.demo.adeline.model.ComentarioLike;
import com.demo.adeline.repository.ComentarioLikeRepository;
import com.demo.adeline.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comentario-likes")
public class ComentarioLikeController {

    @Autowired
    private ComentarioLikeRepository likeRepository;

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleLike(@RequestParam Long comentarioId, @RequestParam Long usuarioId) {
        try {
            Optional<ComentarioLike> existingLike = likeRepository.findByComentarioIdAndUsuarioId(comentarioId, usuarioId);

            if (existingLike.isPresent()) {
                // Si existe, lo quitamos (Unlike)
                likeRepository.delete(existingLike.get());
                return ResponseEntity.ok("Like quitado");
            } else {
                // Si no existe, lo ponemos (Like)
                ComentarioLike nuevoLike = new ComentarioLike(comentarioId, usuarioId);
                likeRepository.save(nuevoLike);
                return ResponseEntity.ok("Like agregado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar el like: " + e.getMessage());
        }
    }

    @GetMapping("/estado")
    public ResponseEntity<?> verificarEstado(@RequestParam Long comentarioId, @RequestParam Long usuarioId) {
        boolean existe = likeRepository.findByComentarioIdAndUsuarioId(comentarioId, usuarioId).isPresent();
        return ResponseEntity.ok(existe);
    }
}