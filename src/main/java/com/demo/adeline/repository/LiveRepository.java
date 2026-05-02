package com.demo.adeline.repository;

import com.demo.adeline.model.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LiveRepository extends JpaRepository<Live, Long> {
    // Buscar todos los que están transmitiendo actualmente
    List<Live> findByEstado(String estado);

    // Buscar si un usuario ya tiene un live activo
    Optional<Live> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}