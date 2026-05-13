package com.demo.adeline.repository;

import com.demo.adeline.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    // Esto busca videos filtrando por el ID del usuario ligado
    List<Video> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);
}