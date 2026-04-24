package com.demo.adeline.controller;

import com.demo.adeline.model.Video;
import com.demo.adeline.model.Usuario;
import com.demo.adeline.repository.VideoRepository;
import com.demo.adeline.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoRepository videoRepo;
    private final UsuarioRepository usuarioRepo;
    
    // Carpeta temporal permitida en Render
    private final String UPLOAD_DIR = "/tmp/uploads/";

    // Constructor (Inyección de dependencias)
    public VideoController(VideoRepository videoRepo, UsuarioRepository usuarioRepo) {
        this.videoRepo = videoRepo;
        this.usuarioRepo = usuarioRepo;
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

        // 2. Asegurar que el directorio de subida existe
        Path directoryPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // 3. Generar nombre único y guardar archivo
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 4. Crear objeto Video para la base de datos
        Video v = new Video();
        v.setTitulo(file.getOriginalFilename());
        // URL que WebConfig servirá desde /tmp/uploads/
        v.setUrlVideo("https://backend-ruth-slam.onrender.com/uploads/" + fileName);
        v.setUsuario(user);
        
        // Dejamos thumbnail nulo o vacío para evitar errores de tamaño de columna
        v.setThumbnail(null); 

        // 5. Guardar en Supabase y retornar
        return ResponseEntity.ok(videoRepo.save(v));
    }
}