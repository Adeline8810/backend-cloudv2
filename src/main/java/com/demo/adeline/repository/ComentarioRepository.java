package com.demo.adeline.repository;

import com.demo.adeline.model.Comentario;
import com.demo.adeline.model.ComentarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Busca comentarios de un video y los pone del más nuevo al más viejo
	@Query("SELECT new com.demo.adeline.model.ComentarioDTO(" +
	           "c.id, c.contenido, c.fecha, c.usuarioId, u.username, u.fotoUrl, c.likesCount) " +
	           "FROM Comentario c JOIN Usuario u ON c.usuarioId = u.id " +
	           "WHERE c.videoId = :videoId " +
	           "ORDER BY c.fecha DESC")
	    List<ComentarioDTO> findComentariosByVideoId(@Param("videoId") Long videoId);
    
    
    
}