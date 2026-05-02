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
    public ResponseEntity<?> toggleLike(
        @RequestParam(name = "comentarioId", required = false) Long comentarioId, 
        @RequestParam(name = "comentarioid", required = false) Long comentarioid, 
        @RequestParam(name = "usuarioId", required = false) Long usuarioId,
        @RequestParam(name = "usuarioid", required = false) Long usuarioid) {
        
        // EXPLICACIÓN: Aquí "atrapamos" el ID sin importar si viene con mayúscula o minúscula
        Long cId = (comentarioId != null) ? comentarioId : comentarioid;
        Long uId = (usuarioId != null) ? usuarioId : usuarioid;

        // Validación de seguridad: si ambos son nulos, avisamos del error
        if (cId == null || uId == null) {
            return ResponseEntity.badRequest().body("Error: IDs no recibidos correctamente. cId: " + cId + ", uId: " + uId);
        }

        try {
            // Buscamos si ya existe el Like en la tabla
            Optional<ComentarioLike> existingLike = likeRepository.findByComentarioIdAndUsuarioId(cId, uId);

            if (existingLike.isPresent()) {
                // Si ya existe, lo borramos (QUITA EL LIKE)
                likeRepository.delete(existingLike.get());
                return ResponseEntity.ok("Like quitado");
            } else {
                // Si no existe, creamos uno nuevo (DA EL LIKE)
                ComentarioLike nuevoLike = new ComentarioLike(cId, uId);
                likeRepository.save(nuevoLike);
                return ResponseEntity.ok("Like agregado");
            }
        } catch (Exception e) {
            // Esto nos dirá exactamente qué rompió el servidor si vuelve a fallar
            e.printStackTrace(); 
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    @GetMapping("/estado")
    public ResponseEntity<?> verificarEstado(@RequestParam Long comentarioId, @RequestParam Long usuarioId) {
        boolean existe = likeRepository.findByComentarioIdAndUsuarioId(comentarioId, usuarioId).isPresent();
        return ResponseEntity.ok(existe);
    }
}