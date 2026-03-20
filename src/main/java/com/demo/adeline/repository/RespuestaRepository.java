package com.demo.adeline.repository;

import com.demo.adeline.model.Respuesta;

import com.demo.adeline.model.Respuesta;
import com.demo.adeline.model.RespuestaAmigoDTO; // Importa el DTO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	
	
	List<Respuesta> findByUsuarioId(Long usuarioId);
	
	// Este es el nuevo método para verificar si ya respondió una pregunta específica
	List<Respuesta> findByUsuarioIdAndPreguntaId(Long usuarioId, Long preguntaId);
	
	@Query("SELECT new com.demo.adeline.model.RespuestaAmigoDTO(" +
		       "(SELECT p.texto FROM Pregunta p WHERE p.id = r.preguntaId), " +
		       " r.texto, " +
		       " r.fotoUrl) " +   // ⚠️ IMPORTANTE
		       " FROM Respuesta r " +
		       " WHERE r.usuarioId = (" +
		       "   SELECT u.id FROM Usuario u WHERE u.username = :username" +
		       ") " +
		       "ORDER BY r.preguntaId ASC")
		List<RespuestaAmigoDTO> buscarPorAmigo(@Param("username") String username);
	}

