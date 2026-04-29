package com.demo.adeline.controller;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;
import com.demo.adeline.model.Canto;
import com.demo.adeline.repository.CantoRepository;
import java.util.Map;


@RestController
@RequestMapping("/api/cantos")
@CrossOrigin(origins = "*") // Para que Angular no tenga problemas de permisos
public class CantoController {

    @Autowired
    private Cloudinary cloudinary; // El que ya configuraste para tus fotos/videos
    @Autowired
    private CantoRepository cantoRepository;

    @PostMapping("/subir")
    public ResponseEntity<?> subirAudio(
        @RequestParam("archivo") MultipartFile archivo,
        @RequestParam("usuarioId") Long usuarioId) { // Ahora recibimos el ID
        try {
            // 1. Subir a Cloudinary
        	Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), 
        		    ObjectUtils.asMap("resource_type", "video", "format", "wav"));
          	String urlFinal = uploadResult.get("secure_url").toString();

            // 2. Guardar en DB
            Canto nuevoCanto = new Canto();
            nuevoCanto.setUrlAudio(urlFinal);
            nuevoCanto.setUsuarioId(usuarioId);
            cantoRepository.save(nuevoCanto); // Guarda el link en PostgreSQL

            return ResponseEntity.ok(nuevoCanto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}