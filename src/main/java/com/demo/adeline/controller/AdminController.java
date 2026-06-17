package com.demo.adeline.controller;

import com.demo.adeline.model.Usuario;
import com.demo.adeline.model.Insignia;
import com.demo.adeline.repository.UsuarioRepository;
import com.demo.adeline.repository.InsigniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepo;
    private final InsigniaRepository insigniaRepo;

    // Inyección de dependencias a través del constructor
    public AdminController(UsuarioRepository usuarioRepo, InsigniaRepository insigniaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.insigniaRepo = insigniaRepo;
    }

    // 1. Listar todos los usuarios
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    // 2. Asignar Insignia a un usuario
    @PostMapping("/asignar-insignia/{usuarioId}/{insigniaId}")
    public ResponseEntity<?> asignarInsignia(@PathVariable Long usuarioId, @PathVariable Long insigniaId) {
        return usuarioRepo.findById(usuarioId).map(usuario -> {
            return insigniaRepo.findById(insigniaId).map(insignia -> {
                usuario.getInsignias().add(insignia);
                usuarioRepo.save(usuario);
                return ResponseEntity.ok("Insignia asignada correctamente");
            }).orElse(ResponseEntity.notFound().build());
        }).orElse(ResponseEntity.notFound().build());
    }

    // 3. Eliminar usuario (Mantenimiento)
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepo.existsById(id)) return ResponseEntity.notFound().build();
        usuarioRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Bloquear/Desbloquear usuario 
    // Nota: Necesitas añadir el campo "boolean activo" en tu clase Usuario
    @PutMapping("/usuarios/{id}/estado")
    public ResponseEntity<?> cambiarEstadoUsuario(@PathVariable Long id, @RequestParam boolean activo) {
        return usuarioRepo.findById(id).map(usuario -> {
            // usuario.setActivo(activo); 
            usuarioRepo.save(usuario);
            return ResponseEntity.ok("Estado actualizado");
        }).orElse(ResponseEntity.notFound().build());
    }
}