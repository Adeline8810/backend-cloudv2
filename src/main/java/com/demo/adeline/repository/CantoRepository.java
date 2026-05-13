package com.demo.adeline.repository;


import com.demo.adeline.model.Canto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface CantoRepository extends JpaRepository<Canto, Long> {
    // Aquí puedes agregar métodos personalizados después, como buscar por usuarioId
	List<Canto> findByUsuarioId(Long usuarioId);
}
