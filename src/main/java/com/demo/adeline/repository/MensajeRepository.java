package com.demo.adeline.repository;

import com.demo.adeline.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    
    // Esta consulta busca todos los mensajes entre el usuario A y el usuario B
    @Query("SELECT m FROM Mensaje m WHERE (m.emisorId = ?1 AND m.receptorId = ?2) OR (m.emisorId = ?2 AND m.receptorId = ?1) ORDER BY m.fecha ASC")
    List<Mensaje> obtenerHistorial(Long id1, Long id2);
}