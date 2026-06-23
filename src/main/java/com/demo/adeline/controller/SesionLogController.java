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
        
        // 1. Configurar la IP de respaldo por si el GPS falla
        String ipUsuario = request.getHeader("X-Forwarded-For");
        if (ipUsuario == null || ipUsuario.isEmpty() || "unknown".equalsIgnoreCase(ipUsuario)) {
            ipUsuario = request.getRemoteAddr();
        }
        if (ipUsuario.equals("0:0:0:0:0:0:0:1") || ipUsuario.equals("127.0.0.1")) {
            ipUsuario = "90.85.124.0"; // IP de prueba de Francia
        }
        log.setIpAddress(ipUsuario);

        RestTemplate restTemplate = new RestTemplate();

        // 2. ¿Llegaron coordenadas GPS desde Angular?
        if (log.getLatitud() != null && log.getLongitud() != null) {
            try {
                // Usamos la API pública de OpenStreetMap (Nominatim) de servidor a servidor
                String urlGps = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" 
                                + log.getLatitud() + "&lon=" + log.getLongitud();
                
                // Nominatim requiere obligatoriamente una cabecera User-Agent para no bloquear la petición
                // Así que hacemos la consulta obteniendo el mapa de respuesta
                Map<String, Object> response = restTemplate.getForObject(urlGps, Map.class);
                
                if (response != null && response.containsKey("address")) {
                    Map<String, Object> address = (Map<String, Object>) response.get("address");
                    
                    // Buscamos la ciudad exacta en la respuesta (a veces viene como 'city', 'town' o 'village')
                    String ciudadExacta = "Aurillac"; // Valor por defecto
                    if (address.containsKey("city")) ciudadExacta = (String) address.get("city");
                    else if (address.containsKey("town")) ciudadExacta = (String) address.get("town");
                    else if (address.containsKey("village")) ciudadExacta = (String) address.get("village");

                    log.setPais((String) address.getOrDefault("country", "France"));
                    log.setCiudad(ciudadExacta);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Falló el mapeo del GPS, usando respaldo de IP: " + e.getMessage());
                obtenerUbicacionPorIp(log, ipUsuario, restTemplate);
            }
        } else {
            // Si no hay datos de GPS, procesamos la geolocalización tradicional por IP
            obtenerUbicacionPorIp(log, ipUsuario, restTemplate);
        }

        // 3. Guardar el log final procesado
        sesionLogRepo.save(log);
        return ResponseEntity.ok().body("{\"status\": \"Visita registrada exitosamente\"}");
    }

    // Método auxiliar de respaldo basado en la IP
    private void obtenerUbicacionPorIp(SesionLog log, String ip, RestTemplate restTemplate) {
        try {
            String urlGeo = "https://ipapi.co/" + ip + "/json/";
            Map<String, Object> response = restTemplate.getForObject(urlGeo, Map.class);
            System.out.println("Respuesta ipapi: " + response);
            if (response != null && !response.containsKey("error")) {
                log.setPais((String) response.getOrDefault("country_name", "France"));
                //log.setCiudad((String) response.getOrDefault("city", "Gerardmer"));
                log.setCiudad((String) response.getOrDefault("city", "Inconnue"));
            } else {
                log.setPais("France");
               // log.setCiudad("Gerardmer");
                log.setCiudad("Inconnue");
            }
        } catch (Exception e) {
            log.setPais("France");
            log.setCiudad("Gerardmer");
        }
    }
}