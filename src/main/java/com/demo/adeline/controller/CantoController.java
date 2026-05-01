package com.demo.adeline.controller;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;
import com.demo.adeline.model.Canto;
import com.demo.adeline.repository.CantoRepository;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cantos")

public class CantoController {

    @Autowired
    private Cloudinary cloudinary; // El que ya configuraste para tus fotos/videos
    @Autowired
    private CantoRepository cantoRepository;

    @PostMapping("/subir")
    public ResponseEntity<?> subirCanto(
        @RequestParam("archivo") MultipartFile archivo,
        @RequestParam("usuarioId") Long usuarioId,
        @RequestParam(value = "tipo", defaultValue = "audio") String tipo) { 
        try {
            // 1. Configuración de Cloudinary
            // Para video usamos "video" como resource_type. Cloudinary detecta si es mp4/webm/wav.
            Map options = ObjectUtils.asMap(
                "resource_type", "video", 
                "folder", "karaoke_covers"
            );

            Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), options);
            String urlFinal = uploadResult.get("secure_url").toString();

            // 2. Guardar en la base de datos según el tipo
            Canto nuevoCanto = new Canto();
            nuevoCanto.setUsuarioId(usuarioId);
            nuevoCanto.setTipo(tipo); // 'audio' o 'video'

            if ("video".equalsIgnoreCase(tipo)) {
                nuevoCanto.setUrlVideo(urlFinal); // Va a la nueva columna
                nuevoCanto.setUrlAudio(null); 
            } else {
                nuevoCanto.setUrlAudio(urlFinal); // Lógica anterior
                nuevoCanto.setUrlVideo(null);
            }

            cantoRepository.save(nuevoCanto);
            return ResponseEntity.ok(nuevoCanto);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Canto>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(cantoRepository.findByUsuarioId(usuarioId));
    }
    
}