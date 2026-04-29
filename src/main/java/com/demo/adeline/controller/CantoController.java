package com.demo.adeline.controller;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/cantos")
@CrossOrigin(origins = "*") // Para que Angular no tenga problemas de permisos
public class CantoController {

    @Autowired
    private Cloudinary cloudinary; // El que ya configuraste para tus fotos/videos

    @PostMapping("/subir")
    public ResponseEntity<?> subirAudio(@RequestParam("archivo") MultipartFile archivo) {
        try {
            // 1. Subimos el archivo .wav a Cloudinary
            // Importante: usamos "resource_type", "video" porque Cloudinary mete audios en esa categoría
            Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), 
                ObjectUtils.asMap("resource_type", "auto")); 

            String urlFinal = uploadResult.get("url").toString();

            // Por ahora, solo devolveremos la URL para probar que funciona
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("url", urlFinal);
            
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir audio: " + e.getMessage());
        }
    }
}