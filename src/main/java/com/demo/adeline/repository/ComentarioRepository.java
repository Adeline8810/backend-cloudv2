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
		       "c.id, c.contenido, c.fecha, c.usuarioId, u.username, u.fotoUrl, " +
		       "(SELECT COUNT(l) FROM ComentarioLike l WHERE l.comentarioId = c.id), " + // Cuenta total de likes
		       "(SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM ComentarioLike l " +
		       " WHERE l.comentarioId = c.id AND l.usuarioId = :usuarioLogueadoId)) " +   // ¿El usuario actual le dio like?
		       "FROM Comentario c JOIN Usuario u ON c.usuarioId = u.id " +
		       "WHERE c.videoId = :videoId " +
		       "ORDER BY c.fecha DESC")
		List<ComentarioDTO> findComentariosConUsuarioYLike(
		    @Param("videoId") Long videoId, 
		    @Param("usuarioLogueadoId") Long usuarioLogueadoId);
    
    
    
}

