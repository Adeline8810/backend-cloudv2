package com.demo.adeline.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.demo.adeline.model.UsuarioSala;
import com.demo.adeline.model.UsuarioSalaDTO;
import com.demo.adeline.service.UsuarioSalaService;



@RestController
@RequestMapping("/api/usuariosSala")

public class UsuarioSalaController {

    private final UsuarioSalaService service;

    public UsuarioSalaController(UsuarioSalaService service) {
        this.service = service;
    }

    @PostMapping("/entrar")
    public void entrar(@RequestBody UsuarioSalaDTO dto){

        service.entrar(dto);

    }

    @PostMapping("/salir")
    public void salir(@RequestBody UsuarioSalaDTO dto){

        service.salir(dto.getSalaId(),dto.getUsuarioId());

    }

    @GetMapping("/{salaId}")
    public List<UsuarioSala> listar(@PathVariable Long salaId){

        return service.listar(salaId);

    }

}