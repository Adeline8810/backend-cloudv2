

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
@RequestMapping("/api/canciones")
@CrossOrigin(origins = "*") // Importante para que Angular no dé error de CORS
public class CancionController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CancionRepository cancionRepository;

    @PostMapping("/subir")
    public ResponseEntity<?> subirCancion(
            @RequestParam("audio") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("letra_json") String letraJson) {

        try {
            // 1. Subir a Cloudinary (especificando resource_type video para audios)
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                ObjectUtils.asMap(
                    "resource_type", "video",
                    "folder", "slam_girls_audio"
                ));

            // 2. Obtener la URL segura
            String secureUrl = (String) uploadResult.get("secure_url");

            // 3. Guardar en la DB
            Cancion nuevaCancion = new Cancion();
            nuevaCancion.setTitulo(titulo);
            nuevaCancion.setUrlAudio(secureUrl);
            nuevaCancion.setLetraJson(letraJson);
            // nuevaCancion.setArtista("..."); // Opcional

            cancionRepository.save(nuevaCancion);

            return ResponseEntity.ok(nuevaCancion);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la canción: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(cancionRepository.findAll());
    }
}