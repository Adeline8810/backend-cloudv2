package com.demo.adeline.repository;

import com.demo.adeline.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    
    // Esta consulta busca todos los mensajes entre el usuario A y el usuario B
    @Query("SELECT m FROM Mensaje m WHERE (m.emisorId = ?1 AND m.receptorId = ?2) OR (m.emisorId = ?2 AND m.receptorId = ?1) ORDER BY m.fecha ASC")
    List<Mensaje> obtenerHistorial(Long id1, Long id2);
    
    
    
    
    
    @Query(value = "WITH ultimos_mensajes AS (" +
            "    SELECT DISTINCT ON (LEAST(emisor_id, receptor_id), GREATEST(emisor_id, receptor_id)) " +
            "           id, texto, emisor_id, receptor_id, hora, fecha " +
            "    FROM mensajes " +
            "    WHERE emisor_id = :usuarioId OR receptor_id = :usuarioId " +
            "    ORDER BY LEAST(emisor_id, receptor_id), GREATEST(emisor_id, receptor_id), fecha DESC" +
            ")" +
            "SELECT um.texto AS ultimoTexto, um.hora AS hora, " +
            "       u.id AS contactoId, u.username AS contactonombre, u.foto_url AS fotoUrl, " +
            "       (SELECT COUNT(*) FROM mensajes m " +
            "        WHERE m.emisor_id = u.id AND m.receptor_id = :usuarioId AND m.id > COALESCE(" +
            "            (SELECT MAX(id) FROM mensajes m2 WHERE m2.emisor_id = :usuarioId AND m2.receptor_id = u.id), 0" +
            "        )) AS mensajesNuevos " +
            "FROM ultimos_mensajes um " +
            "JOIN usuarios u ON (u.id = CASE WHEN um.emisor_id = :usuarioId THEN um.receptor_id ELSE um.emisor_id END) " +
            "ORDER BY um.fecha DESC", nativeQuery = true)
    List<Map<String, Object>> findConversacionesRecientes(@Param("usuarioId") Long usuarioId);
    
    
    
    
}