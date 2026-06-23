package com.demo.adeline.service;

import com.demo.adeline.model.SalaConversacion;
import com.demo.adeline.repository.SalaConversacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaConversacionService {

    @Autowired
    private SalaConversacionRepository salaRepository;

    public void agregarRegalos(Long salaId, Long cantidad) {

        SalaConversacion sala =
            salaRepository.findById(salaId).orElseThrow();

        sala.setPuntosSala(
            sala.getPuntosSala() + cantidad
        );

        recalcularNivel(sala);

        salaRepository.save(sala);
    }

    public void agregarTiempo(Long salaId, Long minutos) {

        SalaConversacion sala =
            salaRepository.findById(salaId).orElseThrow();

        sala.setMinutosActivos(
            sala.getMinutosActivos() + minutos
        );

        recalcularNivel(sala);

        salaRepository.save(sala);
    }

    private void recalcularNivel(
        SalaConversacion sala
    ) {

        long puntos =
            sala.getPuntosSala()
            + sala.getMinutosActivos();

        if (puntos >= 50000) {
            sala.setNivel(10);
            sala.setCantidadAsientos(16);
        }
        else if (puntos >= 30000) {
            sala.setNivel(9);
            sala.setCantidadAsientos(14);
        }
        else if (puntos >= 20000) {
            sala.setNivel(8);
            sala.setCantidadAsientos(12);
        }
        else if (puntos >= 10000) {
            sala.setNivel(7);
            sala.setCantidadAsientos(10);
        }
        else if (puntos >= 5000) {
            sala.setNivel(6);
            sala.setCantidadAsientos(9);
        }
        else if (puntos >= 2000) {
            sala.setNivel(5);
            sala.setCantidadAsientos(8);
        }
        else if (puntos >= 1000) {
            sala.setNivel(4);
            sala.setCantidadAsientos(7);
        }
        else if (puntos >= 500) {
            sala.setNivel(3);
            sala.setCantidadAsientos(6);
        }
        else if (puntos >= 200) {
            sala.setNivel(2);
            sala.setCantidadAsientos(5);
        }
        else {
            sala.setNivel(1);
            sala.setCantidadAsientos(4);
        }
    }
}