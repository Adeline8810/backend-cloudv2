package com.demo.adeline.service;

import com.demo.adeline.model.Live;
import com.demo.adeline.repository.LiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;

    public Live iniciarLive(Long usuarioId, String titulo) {
        // 1. Buscamos TODOS los lives activos de ese usuario y los cerramos de golpe
        // Esto evita el error 500 si hay datos duplicados
        List<Live> activos = liveRepository.findByEstado("EN_VIVO");
        for (Live l : activos) {
            if (l.getUsuarioId().equals(usuarioId)) {
                l.setEstado("FINALIZADO");
                liveRepository.save(l);
            }
        }

        // 2. Ahora sí creamos el nuevo
        Live nuevoLive = new Live();
        nuevoLive.setUsuarioId(usuarioId);
        nuevoLive.setTitulo(titulo);
        nuevoLive.setEstado("EN_VIVO");
        nuevoLive.setStreamKey(String.valueOf(usuarioId));
        
        return liveRepository.save(nuevoLive);
    }

    public void finalizarLivePorUsuario(Long usuarioId) {
        liveRepository.findByUsuarioIdAndEstado(usuarioId, "EN_VIVO")
            .ifPresent(live -> {
                live.setEstado("FINALIZADO");
                liveRepository.save(live);
            });
    }

    public void finalizarLive(Long liveId) {
        liveRepository.findById(liveId).ifPresent(live -> {
            live.setEstado("FINALIZADO");
            liveRepository.save(live);
        });
    }
}