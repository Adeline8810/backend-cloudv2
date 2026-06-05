package com.demo.adeline.controller;

import com.demo.adeline.model.HistorialRegalo;
import com.demo.adeline.repository.HistorialRegaloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-regalos")

public class HistorialRegaloController {

    @Autowired
    private HistorialRegaloRepository historialRepo;

    // Consultar regalos recibidos por el usuario (streamer)
    @GetMapping("/recibidos/{destinatarioId}")
    public List<HistorialRegalo> obtenerRecibidos(@PathVariable Long destinatarioId) {
        return historialRepo.findByIdDestinatario(destinatarioId);
    }

    // Consultar regalos enviados por el usuario
    @GetMapping("/enviados/{regaladorId}")
    public List<HistorialRegalo> obtenerEnviados(@PathVariable Long regaladorId) {
        return historialRepo.findByIdRegalador(regaladorId);
    }
}