package com.demo.adeline.controller;

import com.demo.adeline.model.SesionLog;
import com.demo.adeline.repository.SesionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
//@CrossOrigin(origins = {"https://sparkling-t28w.onrender.com", "https://adeline8810.github.io", "http://localhost:4200"})
public class SesionLogController {

    @Autowired
    private SesionLogRepository sesionLogRepo;

    @PostMapping("/registrar-visita")
    public ResponseEntity<?> registrarVisita(@RequestBody SesionLog log) {
        sesionLogRepo.save(log);
        return ResponseEntity.ok().body("{\"status\": \"Visita registrada exitosamente\"}");
    }
}