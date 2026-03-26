package com.demo.adeline.controller;

import com.demo.adeline.model.Usuario;
import com.demo.adeline.model.UsuarioBusquedaDTO;
import com.demo.adeline.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
//@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "*")

public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario u) {
        if (u.getUsername() == null || u.getEmail() == null || u.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (repo.findByUsername(u.getUsername()).isPresent() || repo.findByEmail(u.getEmail()).isPresent()) {
            return ResponseEntity.status(409).build(); // conflict
        }

        Usuario saved = repo.save(u);
        return ResponseEntity.created(URI.create("/api/usuarios/" + saved.getId())).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario u) {
        if (u.getUsername() == null || u.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> found = repo.findByUsernameAndPassword(u.getUsername(), u.getPassword());
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getOne(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario u) {
        return repo.findById(id).map(existing -> {
            existing.setNombre(u.getNombre());
            existing.setEmail(u.getEmail());
            if (u.getPassword() != null && !u.getPassword().isEmpty()) {
                existing.setPassword(u.getPassword());
            }
            return ResponseEntity.ok(repo.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
 // ESTE ES EL MÉTODO QUE ME PREGUNTASTE
    @GetMapping("/buscar-usuarios")
    public List<UsuarioBusquedaDTO> buscarUsuarios(@RequestParam String nombre) {
        return repo.buscarUsuariosPorTermino(nombre); 
    }
    
}
