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
@CrossOrigin(origins = "*")
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
}
