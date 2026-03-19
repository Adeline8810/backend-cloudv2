package com.demo.adeline.controller;

import com.demo.adeline.model.Pregunta;
import com.demo.adeline.repository.PreguntaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
//@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "https://Adeline8810.github.io")
public class PreguntaController {

    private final PreguntaRepository repo;

    public PreguntaController(PreguntaRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Pregunta> listar() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Pregunta> crear(@RequestBody Pregunta p) {
        Pregunta saved = repo.save(p);
        return ResponseEntity.created(URI.create("/api/preguntas/" + saved.getId())).body(saved);
    }
    
    
    // Obtener una pregunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pregunta> getOne(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
    // Actualizar pregunta
    @PutMapping("/{id}")
    public ResponseEntity<Pregunta> actualizar(@PathVariable Long id, @RequestBody Pregunta p) {
        return repo.findById(id).map(existing -> {
            existing.setTexto(p.getTexto());
            return ResponseEntity.ok(repo.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar pregunta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
