package com.demo.adeline.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/canto")
public class CantoController {

    @PostMapping("/subir")
    public ResponseEntity<String> subirCanto(@RequestParam("file") MultipartFile file, @RequestParam("usuarioId") Long id) {
        // Aquí usarás tu lógica para subir el archivo a Supabase Storage
        // Es igual a como subes las fotos de perfil
        return ResponseEntity.ok("Canto guardado con éxito");
    }
}