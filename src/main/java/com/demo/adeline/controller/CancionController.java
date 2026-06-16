

package com.demo.adeline.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.demo.adeline.model.Cancion;
import com.demo.adeline.repository.CancionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@RestController
@RequestMapping("/api/cancion")

public class CancionController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CancionRepository cancionRepository;

    @PostMapping("/subir")
    public ResponseEntity<?> subirCancion(
            @RequestParam("audio") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("artista") String artista,
            @RequestParam("letra_json") String letraJson,
            @RequestParam(value = "portada", required = false) MultipartFile portada,
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("username") String username,
            @RequestParam(value = "usuarioFoto", required = false) String usuarioFoto) {

        try {

            // ==========================
            // SUBIR AUDIO A CLOUDINARY
            // ==========================
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "video",
                            "folder", "slam_girls_audio"
                    ));

            String secureUrl = (String) uploadResult.get("secure_url");

            // ==========================
            // SUBIR PORTADA SI EXISTE
            // ==========================
            String portadaUrl = null;

            if (portada != null && !portada.isEmpty()) {

                Map portadaUpload = cloudinary.uploader().upload(
                        portada.getBytes(),
                        ObjectUtils.asMap(
                                "folder", "portadas_canciones"
                        ));

                portadaUrl = (String) portadaUpload.get("secure_url");
            }

            // ==========================
            // GUARDAR EN BASE DE DATOS
            // ==========================
            Cancion nuevaCancion = new Cancion();

            nuevaCancion.setTitulo(titulo);
            nuevaCancion.setArtista(artista);
            nuevaCancion.setUrlAudio(secureUrl);
            nuevaCancion.setLetraJson(letraJson);

            // NUEVOS CAMPOS
            nuevaCancion.setPortadaUrl(portadaUrl);
            nuevaCancion.setUsuarioId(usuarioId);
            nuevaCancion.setUsername(username);
            nuevaCancion.setUsuarioFoto(usuarioFoto);

            cancionRepository.save(nuevaCancion);

            return ResponseEntity.ok(nuevaCancion);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(500)
                    .body("Error al procesar la canción: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(cancionRepository.findAll());
    }

    // Agregado para que el Karaoke pueda buscar la canción por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return cancionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}