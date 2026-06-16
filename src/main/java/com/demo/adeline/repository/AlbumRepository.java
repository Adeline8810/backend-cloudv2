package com.demo.adeline.repository;



import com.demo.adeline.model.AlbumFoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumFoto, Long> {

    // Método para traer todas las fotos de un usuario específico
    List<AlbumFoto> findByUsuarioId(Long usuarioId);

    // Método útil para buscar la foto que está marcada como portada actualmente
    Optional<AlbumFoto> findByUsuarioIdAndEsPortadaTrue(Long usuarioId);
}