package com.demo.adeline.repository;

import com.demo.adeline.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Busca comentarios de un video y los pone del más nuevo al más viejo
    List<Comentario> findByVideoIdOrderByFechaDesc(Long videoId);
}