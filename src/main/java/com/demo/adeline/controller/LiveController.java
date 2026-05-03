package com.demo.adeline.controller;

import com.demo.adeline.model.Live;
import com.demo.adeline.repository.LiveRepository;
import com.demo.adeline.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lives")
public class LiveController {

    @Autowired
    private LiveService liveService;

    @Autowired
    private LiveRepository liveRepository;

    // 1. Iniciar un Live
    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciar(@RequestParam Long usuarioId, @RequestParam String titulo) {
        try {
            Live live = liveService.iniciarLive(usuarioId, titulo);
            return ResponseEntity.ok(live);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al iniciar live: " + e.getMessage());
        }
    }

    // 2. Finalizar un Live
    @PostMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        try {
            liveService.finalizarLive(id);
            return ResponseEntity.ok("Live finalizado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al finalizar live");
        }
    }
    
 // Agrega este nuevo método para finalizar usando el ID del usuario
    @PostMapping("/finalizar/usuario/{usuarioId}")
    public ResponseEntity<?> finalizarPorUsuario(@PathVariable Long usuarioId) {
        try {
            // Buscamos el live activo de ese usuario y lo cerramos
            java.util.Optional<Live> liveActivo = liveRepository.findByUsuarioIdAndEstado(usuarioId, "EN_VIVO");
            if (liveActivo.isPresent()) {
                liveService.finalizarLive(liveActivo.get().getId());
                return ResponseEntity.ok("Live finalizado");
            }
            return ResponseEntity.ok("No había live activo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
    }

    // 3. Listar todos los que están EN VIVO ahora mismo
    @GetMapping("/activos")
    public ResponseEntity<List<Live>> listarActivos() {
        return ResponseEntity.ok(liveRepository.findByEstado("EN_VIVO"));
    }
}