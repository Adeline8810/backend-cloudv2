package com.demo.adeline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.demo.adeline.model.Usuario;
import com.demo.adeline.repository.UsuarioRepository;

import java.util.List;

@CrossOrigin(origins = "${FRONTEND_URL:http://localhost:4200}")
@RestController
@RequestMapping("/api/usuarios")


public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(u -> u.setPassword(null));
        return usuarios;
    }
  

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario detallesUsuario) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(detallesUsuario.getNombre());
                    usuario.setEmail(detallesUsuario.getEmail());
                    usuario.setPassword(detallesUsuario.getPassword());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }
    
    // 🔹 Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // ✅ 204 eliminado correctamente
        } else {
            return ResponseEntity.notFound().build(); // ⚠️ si no existe
        }
    }
    
    
    
}
