package com.demo.adeline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.adeline.model.Respuesta;
import com.demo.adeline.model.RespuestaAmigoDTO; // Importa el DTO

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	
	
	List<Respuesta> findByUsuarioId(Long usuarioId);
	
	// Este es el nuevo método para verificar si ya respondió una pregunta específica
	List<Respuesta> findByUsuarioIdAndPreguntaId(Long usuarioId, Long preguntaId);
	
	@Query("SELECT new com.demo.adeline.model.RespuestaAmigoDTO(" +
		       "p.texto, r.texto, r.fotoUrl) " +
		       "FROM Respuesta r " +
		       "JOIN Pregunta p ON p.id = r.preguntaId " +
		       "JOIN Usuario u ON u.id = r.usuarioId " +
		       "WHERE u.username = :username " +
		       "ORDER BY r.preguntaId ASC")
		List<RespuestaAmigoDTO> buscarPorAmigo(@Param("username") String username);
	}

