package com.demo.adeline.repository;


import com.demo.adeline.model.Canto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface CantoRepository extends JpaRepository<Canto, Long> {
    // Aquí puedes agregar métodos personalizados después, como buscar por usuarioId
	List<Canto> findByUsuarioId(Long usuarioId);
	
	
	
	
	// Busca los cantos de un usuario que NO estén eliminados
    List<Canto> findByUsuarioIdAndEstadoNot(Long usuarioId, String estado);
    
    // Opcional: Si quieres listar todos en la app pero ignorando los eliminados
    List<Canto> findByEstadoNot(String estado);
	
    List<Canto> findAllByOrderByFechaCreacionDesc();
    
	
}
