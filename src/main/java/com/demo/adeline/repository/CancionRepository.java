package com.demo.adeline.repository;

import com.demo.adeline.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {
    
    // Spring Boot creará automáticamente la lógica para buscar por título si lo necesitas
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    
    // También podrías buscar por artista
    List<Cancion> findByArtistaContainingIgnoreCase(String artista);
}