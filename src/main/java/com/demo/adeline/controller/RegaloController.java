package com.demo.adeline.controller;


import com.demo.adeline.model.CatalogoRegalos;
import com.demo.adeline.model.HistorialRegalo;
import com.demo.adeline.model.RegaloRequest;
import com.demo.adeline.repository.CatalogoRegalosRepository;
import com.demo.adeline.repository.HistorialRegaloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regalos")
@CrossOrigin(origins = "*")
public class RegaloController {

    @Autowired
    private CatalogoRegalosRepository catalogoRepo;

    @Autowired
    private HistorialRegaloRepository historialRepo;

    // Obtener catálogo disponible
    @GetMapping("/lista")
    public List<CatalogoRegalos> listarRegalos() {
        return catalogoRepo.findAll();
    }

    // Registrar envío de regalo
    @PostMapping("/enviar")
    public String enviarRegalo(@RequestBody RegaloRequest request) {
        HistorialRegalo historial = new HistorialRegalo();
        historial.setIdRegalador(request.getRegaladorId());
        historial.setIdDestinatario(request.getDestinatarioId());
        historial.setIdRegalo(request.getIdRegalo());
        
        historialRepo.save(historial);
        return "OK";
    }
}