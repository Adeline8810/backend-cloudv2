package com.demo.adeline.service;

import com.demo.adeline.model.Live;
import com.demo.adeline.repository.LiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;

    public Live iniciarLive(Long usuarioId, String titulo) {
        // 1. Si ya tiene algo activo, lo marcamos como FINALIZADO primero para no duplicar
        liveRepository.findByUsuarioIdAndEstado(usuarioId, "EN_VIVO")  
            .ifPresent(live -> {
                live.setEstado("FINALIZADO");
                liveRepository.save(live);
            });

        // 2. Creamos el nuevo registro
        Live nuevoLive = new Live();
        nuevoLive.setUsuarioId(usuarioId);
        nuevoLive.setTitulo(titulo);
        nuevoLive.setEstado("EN_VIVO");
        nuevoLive.setStreamKey(UUID.randomUUID().toString()); // Esto servirá de ID para PeerJS
        
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