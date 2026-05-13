package com.demo.adeline.controller;

import com.demo.adeline.model.Live;
import com.demo.adeline.repository.LiveRepository;
import com.demo.adeline.service.LiveKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/livekit")
public class LiveKitController {

    @Autowired
    private LiveKitService liveKitService;

    @Autowired
    private LiveRepository liveRepository;

    // GENERAR TOKEN (Igual que antes)
    @GetMapping("/token")
    public Map<String, String> getToken(@RequestParam String room, @RequestParam String identity) {
        String token = liveKitService.createToken(room, identity);
        return Map.of("token", token);
    }

    // INICIAR LIVE (Usuario 1 - Botón +)
    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarLive(@RequestBody Map<String, Object> payload) {
        try {
            Live nuevoLive = new Live();
            nuevoLive.setUsuarioId(Long.valueOf(payload.get("usuarioId").toString()));
            nuevoLive.setTitulo(payload.get("titulo").toString());
            
            // Generamos la sala automática
            String roomName = "sala_" + System.currentTimeMillis();
            nuevoLive.setStreamKey(roomName);
            nuevoLive.setEstado("EN_VIVO");

            liveRepository.save(nuevoLive);
            return ResponseEntity.ok(nuevoLive);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // LISTAR LIVES ACTIVOS (Usuario 2 - Círculos)
    @GetMapping("/activos")
    public ResponseEntity<?> listarActivos() {
        return ResponseEntity.ok(liveRepository.findByEstado("EN_VIVO"));
    }

    // FINALIZAR LIVE (Al salir)
    @PostMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        return liveRepository.findById(id).map(live -> {
            live.setEstado("FINALIZADO");
            liveRepository.save(live);
            return ResponseEntity.ok("Live finalizado");
        }).orElse(ResponseEntity.notFound().build());
    }
}