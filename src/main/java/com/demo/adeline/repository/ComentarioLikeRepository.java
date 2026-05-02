package com.demo.adeline.repository;

import com.demo.adeline.model.ComentarioLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComentarioLikeRepository extends JpaRepository<ComentarioLike, Long> {
    
    // Para verificar si el like ya existe
    Optional<ComentarioLike> findByComentarioIdAndUsuarioId(Long comentarioId, Long usuarioId);
    
    // Para contar cuántos likes tiene un comentario
    long countByComentarioId(Long comentarioId);
}