package com.demo.adeline.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.Map;

@Controller
public class ChatController {

    @MessageMapping("/enviar-mensaje") // Tu Angular enviará a /app/enviar-mensaje
    @SendTo("/topic/mensajes")         // Se reenvía a quienes escuchan /topic/mensajes
    public Map<String, Object> procesarMensaje(Map<String, Object> mensaje) {
        // El mensaje llega como un objeto JSON (clave-valor)
        System.out.println("Mensaje recibido: " + mensaje.get("texto"));
        return mensaje; 
    }
}