package com.demo.adeline.controller;

import com.demo.adeline.model.AlbumFoto;
import com.demo.adeline.repository.AlbumRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api/album")
public class AlbumController {
    @Autowired private AlbumRepository repo;
    @Autowired private Cloudinary cloudinary;

    @PostMapping("/upload/{usuarioId}")
    public ResponseEntity<?> subirFoto(@RequestParam("file") MultipartFile file, @PathVariable Long usuarioId) throws IOException {
        var result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "album"));
        AlbumFoto foto = new AlbumFoto();
        foto.setUsuarioId(usuarioId);
        foto.setUrlFoto(result.get("secure_url").toString());
        return ResponseEntity.ok(repo.save(foto));
    }

    @GetMapping("/{usuarioId}")
    public List<AlbumFoto> listarFotos(@PathVariable Long usuarioId) {
        return repo.findByUsuarioId(usuarioId);
    }
    
    @DeleteMapping("/{fotoId}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable Long fotoId) {

        AlbumFoto foto = repo.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));

        repo.delete(foto);

        return ResponseEntity.noContent().build();
    }
    
    
    
    
}
