package com.demo.adeline.repository;

import com.demo.adeline.model.Respuesta;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	
	
	List<Respuesta> findByUsuarioId(Long usuarioId);
	
	// Este es el nuevo método para verificar si ya respondió una pregunta específica
	List<Respuesta> findByUsuarioIdAndPreguntaId(Long usuarioId, Long preguntaId);
}
