package com.demo.adeline.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Service
public class LiveKitService {

    // Datos obtenidos de tu imagen image_8647cb.png
    private String apiKey = "APIWwmmky7uVucE"; 
    
    // IMPORTANTE: Aquí debes pegar el código que aparece al darle a "Reveal secret"
    private String apiSecret = "MUdaEnibq2LbMAiGZuu5zEuZDY1DUbt4VCWJLn7aZKF"; 

    /**
     * Genera un token JWT compatible con LiveKit para acceder a la sala.
     */
    public String createToken(String roomName, String participantName) {
        
        // Configuramos los permisos (VideoGrants) para que el usuario pueda hablar y ver
        Map<String, Object> videoGrant = Map.of(
            "room", roomName,
            "roomJoin", true,
            "canPublish", true,
            "canSubscribe", true
        );

        // Generamos el JWT firmado con HMAC256
        return JWT.create()
                .withIssuer(apiKey) // Tu API Key es el emisor
                .withSubject(participantName) // El ID único del participante
                .withClaim("name", participantName) // Nombre que se verá en la sala
                .withClaim("video", videoGrant) // Permisos de video otorgados
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Token válido por 1 hora
                .sign(Algorithm.HMAC256(apiSecret)); // Firma con tu Secret
    }
}