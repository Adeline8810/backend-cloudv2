package com.demo.adeline.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.adeline.model.SesionLog;
import com.demo.adeline.repository.SesionLogRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/analytics")
//@CrossOrigin(origins = {"https://sparkling-t28w.onrender.com", "https://adeline8810.github.io", "http://localhost:4200"})
public class SesionLogController {

    @Autowired
    private SesionLogRepository sesionLogRepo;

    @PostMapping("/registrar-visita")
    public ResponseEntity<?> registrarVisita(@RequestBody SesionLog log, HttpServletRequest request) {
        
        // 1. Obtener la IP real del cliente que llega al servidor
        String ipUsuario = request.getHeader("X-Forwarded-For");
        if (ipUsuario == null || ipUsuario.isEmpty() || "unknown".equalsIgnoreCase(ipUsuario)) {
            ipUsuario = request.getRemoteAddr();
        }

        // Si estás probando en local (localhost), la IP suele ser 0:0:0:0:0:0:0:1 o 127.0.0.1
        if (ipUsuario.equals("0:0:0:0:0:0:0:1") || ipUsuario.equals("127.0.0.1")) {
            // Ponemos una IP real de Francia para tus pruebas locales
            ipUsuario = "90.85.124.0"; 
        } else if (ipUsuario.contains(",")) {
            // Si viene una cadena de IPs por proxies, agarramos la primera
            ipUsuario = ipUsuario.split(",")[0].trim();
        }

        log.setIpAddress(ipUsuario);

        // 2. Consultar el país desde el Servidor (Spring) para saltarnos el AdBlock
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlGeo = "https://ipapi.co/" + ipUsuario + "/json/";
            
            // Hacemos la petición HTTP de servidor a servidor
            Map<String, Object> response = restTemplate.getForObject(urlGeo, Map.class);
            
            if (response != null && !response.containsKey("error")) {
                log.setPais((String) response.getOrDefault("country_name", "France"));
                log.setCiudad((String) response.getOrDefault("city", "Gerardmer"));
            } else {
                // Valores de respaldo si la API falla o da cuota límite
                log.setPais("France");
                log.setCiudad("Gerardmer");
            }
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo geolocalizar por API, aplicando valores por defecto: " + e.getMessage());
            log.setPais("France");
            log.setCiudad("Gerardmer");
        }

        // 3. Guardar el registro limpio en Supabase
        sesionLogRepo.save(log);
        
        return ResponseEntity.ok().body("{\"status\": \"Visita registrada exitosamente con IP " + ipUsuario + "\"}");
    }
}