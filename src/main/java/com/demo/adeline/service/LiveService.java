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
        // Si ya tiene un live activo, lo cerramos o lo devolvemos (aquí lo devolvemos)
        return liveRepository.findByUsuarioIdAndEstado(usuarioId, "EN_VIVO")
                .orElseGet(() -> {
                    Live nuevoLive = new Live();
                    nuevoLive.setUsuarioId(usuarioId);
                    nuevoLive.setTitulo(titulo);
                    nuevoLive.setEstado("EN_VIVO");
                    // Generamos una clave única para la transmisión
                    nuevoLive.setStreamKey(UUID.randomUUID().toString());
                    return liveRepository.save(nuevoLive);
                });
    }

    public void finalizarLive(Long liveId) {
        liveRepository.findById(liveId).ifPresent(live -> {
            live.setEstado("FINALIZADO");
            liveRepository.save(live);
        });
    }
}