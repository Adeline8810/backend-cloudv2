package com.demo.adeline.repository;

import com.demo.adeline.model.SalaAsiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaAsientoRepository
        extends JpaRepository<SalaAsiento, Long> {

    List<SalaAsiento> findBySalaId(Long salaId);

    Optional<SalaAsiento> findBySalaIdAndNumeroAsiento(
            Long salaId,
            Integer numeroAsiento
    );

    List<SalaAsiento> findByUsuarioId(Long usuarioId);
}