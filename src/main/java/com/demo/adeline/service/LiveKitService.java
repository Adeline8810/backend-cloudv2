package com.demo.adeline.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Service
public class LiveKitService {

    private String apiKey = "devkey";
    private String apiSecret = "secret";

    public String createToken(String roomName, String participantName) {
        // Configuramos los permisos manualmente
        Map<String, Object> videoGrant = Map.of(
            "room", roomName,
            "roomJoin", true,
            "canPublish", true,
            "canSubscribe", true
        );

        return JWT.create()
                .withIssuer(apiKey)
                .withSubject(participantName)
                .withClaim("name", participantName)
                .withClaim("video", videoGrant) // Aquí van los permisos
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // Expira en 1 hora
                .sign(Algorithm.HMAC256(apiSecret));
    }
}