package com.demo.adeline.controller;



import com.demo.adeline.service.LiveKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/livekit")

public class LiveKitController {

    @Autowired
    private LiveKitService liveKitService;

    @GetMapping("/token")
    public Map<String, String> getToken(
            @RequestParam String room, 
            @RequestParam String identity) {
        
        // Llamamos al servicio que acabamos de arreglar
        String token = liveKitService.createToken(room, identity);
        
        // Devolvemos un JSON: {"token": "el_token_aqui"}
        return Map.of("token", token);
    }
}