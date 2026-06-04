package com.demo.adeline.repository;

import com.demo.adeline.model.CatalogoRegalos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRegalosRepository extends JpaRepository<CatalogoRegalos, Long> {
    // Si necesitas buscar por tipo de regalo (ej: 'MONEDA' o 'GRATIS')
    // Spring Boot lo hará automático con esto:
    List<CatalogoRegalos> findByTipo(String tipo);
}