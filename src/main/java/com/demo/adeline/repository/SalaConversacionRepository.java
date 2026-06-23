package com.demo.adeline.repository;

import com.demo.adeline.model.SalaConversacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaConversacionRepository
        extends JpaRepository<SalaConversacion, Long> {

    List<SalaConversacion> findByEstado(String estado);
    
    
    // Este método buscará si ya existe una sala activa para un usuario específico
    List<SalaConversacion> findByUsuarioIdAndEstado(Long usuarioId, String estado);
    
    // Opcional: Si solo quieres obtener una directamente
    Optional<SalaConversacion> findFirstByUsuarioIdAndEstado(Long usuarioId, String estado);
    
}