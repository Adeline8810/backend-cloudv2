package com.demo.adeline.repository;

import com.demo.adeline.model.Usuario;
import com.demo.adeline.model.UsuarioBusquedaDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    
    
 // UsuarioRepository.java
    @Query("SELECT new com.demo.adeline.model.UsuarioBusquedaDTO(u.id, u.nombre, u.email, u.fotoPerfil) " +
           "FROM Usuario u " +
           "WHERE u.nombre LIKE %:termino% OR u.email LIKE %:termino%")
    List<UsuarioBusquedaDTO> buscarUsuariosPorTermino(@Param("termino") String termino);
    
    
   
    
}
