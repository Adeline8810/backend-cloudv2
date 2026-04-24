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
    
    // Carpeta donde se guardarán los videos en Render
    private final String UPLOAD_DIR = "uploads/";

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

        // 1. Buscamos si el usuario existe
        Usuario user = usuarioRepo.findById(usuarioId).orElse(null);
        if (user == null || file.isEmpty()) return ResponseEntity.badRequest().build();

        // 2. Guardar el archivo en el servidor
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // 3. Guardar la información en la tabla 'videos' de Supabase
        Video v = new Video();
        v.setTitulo(file.getOriginalFilename());
        // URL completa para que Angular la pueda reproducir
        v.setUrlVideo("https://backend-ruth-slam.onrender.com/uploads/" + fileName);
        v.setUsuario(user);

        return ResponseEntity.ok(videoRepo.save(v));
    }
}