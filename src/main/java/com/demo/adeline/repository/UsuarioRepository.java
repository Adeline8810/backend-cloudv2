package com.demo.adeline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.adeline.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
