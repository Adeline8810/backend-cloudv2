package com.demo.adeline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.adeline.model.UsuarioSala;



public interface UsuarioSalaRepository extends JpaRepository<UsuarioSala,Long>{

    List<UsuarioSala> findBySalaId(Long salaId);

    Optional<UsuarioSala> findBySalaIdAndUsuarioId(Long salaId,Long usuarioId);

    void deleteBySalaIdAndUsuarioId(Long salaId,Long usuarioId);

}