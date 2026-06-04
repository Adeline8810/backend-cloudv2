package com.demo.adeline.repository;

import com.demo.adeline.model.HistorialRegalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialRegaloRepository extends JpaRepository<HistorialRegalo, Long> {
    
	// Útil para obtener los regalos recibidos por un usuario
    List<HistorialRegalo> findByIdDestinatario(Long idDestinatario);
    
    // Útil para obtener los regalos enviados por un usuario
    List<HistorialRegalo> findByIdRegalador(Long idRegalador);
}