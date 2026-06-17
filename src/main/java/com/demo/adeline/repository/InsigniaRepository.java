package com.demo.adeline.repository;

import com.demo.adeline.model.Insignia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsigniaRepository extends JpaRepository<Insignia, Long> {
    // JpaRepository ya incluye: findAll(), save(), findById(), deleteById(), etc.
    // No necesitas escribir nada más aquí por ahora.
}