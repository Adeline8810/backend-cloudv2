package com.demo.adeline.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.demo.adeline.model.Video;
import com.demo.adeline.model.Usuario;
import com.demo.adeline.repository.VideoRepository;
import com.demo.adeline.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoRepository videoRepo;
    private final UsuarioRepository usuarioRepo;
    private final Cloudinary cloudinary; // 1. Nueva dependencia

    // 2. Constructor actualizado con Cloudinary
    public VideoController(VideoRepository videoRepo, UsuarioRepository usuarioRepo, Cloudinary cloudinary) {
        this.videoRepo = videoRepo;
        this.usuarioRepo = usuarioRepo;
        this.cloudinary = cloudinary;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Video> listarPorUsuario(@PathVariable Long usuarioId) {
        return videoRepo.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }

    @PostMapping("/upload/{usuarioId}")
    public ResponseEntity<Video> uploadVideo(
            @RequestParam("video") MultipartFile file,
            @PathVariable Long usuarioId) throws IOException {

        // 1. Validar usuario y archivo
        Usuario user = usuarioRepo.findById(usuarioId).orElse(null);
        if (user == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // 2. SUBIR A CLOUDINARY
            // "resource_type", "video" es obligatorio para archivos .mp4, .mov, etc.
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                    ObjectUtils.asMap("resource_type", "video"));

            // 3. OBTENER LA URL SEGURA
            String urlCloudinary = (String) uploadResult.get("secure_url");

            // 4. Crear objeto Video para la base de datos
            Video v = new Video();
            v.setTitulo(file.getOriginalFilename());
            
            // Usamos la URL de Cloudinary (esta nunca cambia y no se borra)
            v.setUrlVideo(urlCloudinary); 
            
            v.setUsuario(user);
            v.setThumbnail(null); 

            // 5. Guardar en tu base de datos y retornar
            return ResponseEntity.ok(videoRepo.save(v));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
   
    
}