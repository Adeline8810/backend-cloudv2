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
	
	// NUEVO MÉTODO: Busca por nombre o email (parcial) y devuelve el cuestionario ordenado
	@Query("SELECT new com.demo.adeline.model.RespuestaAmigoDTO(r.pregunta.texto, r.contenido) " +
		       "FROM Respuesta r " +
		       "WHERE r.usuario.nombre LIKE %:termino% OR r.usuario.email LIKE %:termino% " +
		       "ORDER BY r.pregunta.id ASC")
		List<RespuestaAmigoDTO> buscarPorAmigo(@Param("termino") String termino);
	
	
}
