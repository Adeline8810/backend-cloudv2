package com.demo.adeline.controller;

import com.demo.adeline.model.SalaConversacion;
import com.demo.adeline.repository.SalaConversacionRepository;
import com.demo.adeline.service.LiveKitService;
import com.demo.adeline.service.SalaConversacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.adeline.model.SalaAsiento;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salas")
public class SalaConversacionController {

    @Autowired
    private SalaConversacionRepository salaRepository;
    
    @Autowired
    private com.demo.adeline.repository.SalaAsientoRepository asientoRepository;

    @Autowired
    private LiveKitService liveKitService;

    @Autowired
    private SalaConversacionService salaService;

    // ==========================================
    // TOKEN LIVEKIT
    // ==========================================

    @GetMapping("/token")
    public Map<String, String> getToken(
            @RequestParam String room,
            @RequestParam String identity) {

        String token =
                liveKitService.createToken(
                        room,
                        identity
                );

        return Map.of(
                "token",
                token
        );
    }

    // ==========================================
    // CREAR SALA
    // ==========================================

    @PostMapping("/crear")
    public ResponseEntity<?> crearSala(@RequestBody Map<String, Object> payload) {
    	
        Long usuarioId = Long.valueOf(payload.get("usuarioId").toString());
        
        // Verificar si ya tiene una sala activa
        List<SalaConversacion> salasActivas = salaRepository.findByUsuarioIdAndEstado(usuarioId, "ACTIVA");
        
        if (!salasActivas.isEmpty()) {
            return ResponseEntity.status(400).body("Error: El usuario ya tiene una sala activa.");
        }
        try {
        
            SalaConversacion sala =new SalaConversacion();

            sala.setUsuarioId( Long.valueOf( payload.get("usuarioId").toString()));
                   
            sala.setNombre(payload.get("nombre").toString());
                    
            if (payload.containsKey("portadaUrl") && payload.get("portadaUrl") != null) {
                sala.setPortadaUrl(payload.get("portadaUrl").toString());
            } else {
                // Si no enviaste nada, asigna una imagen por defecto
                sala.setPortadaUrl("assets/img/default-sala.png"); 
            }

            String roomName = "sala_" + System.currentTimeMillis();
                    

            sala.setRoomName(roomName);

            sala.setEstado("ACTIVA");

            sala.setModo("DISCUSION");

            sala.setNivel(1);

            sala.setCantidadAsientos(9);

            sala.setPuntosSala(0L);

            sala.setMinutosActivos(0L);
            
            
            
            sala.setTotalUsuarios(0L);

            salaRepository.save(sala);
            
            for (int i = 0; i <= 8; i++) {
                SalaAsiento nuevoAsiento = new SalaAsiento();
                nuevoAsiento.setSalaId(sala.getId());
                nuevoAsiento.setNumeroAsiento(i);
                
                // Valores iniciales para asiento vacío
                nuevoAsiento.setUsuarioId(null);
                nuevoAsiento.setMicroActivo(false);
                nuevoAsiento.setOcupado(false); // <--- Ahora ya existe este campo
                nuevoAsiento.setUsuarioNombre(null);
                nuevoAsiento.setUsuarioFoto(null);

                asientoRepository.save(nuevoAsiento);
            }
            return ResponseEntity.ok(sala);

        

        } catch (Exception e) {

            /*return ResponseEntity
                    .status(500)
                    .body(e.getMessage());*/
        	  e.printStackTrace();
        	    return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ==========================================
    // LISTAR SALAS ACTIVAS
    // ==========================================
    @GetMapping("/activas")
    public ResponseEntity<?> getSalasActivas() {

        try {

            return ResponseEntity.ok(
                salaRepository.findByEstado("ACTIVA")
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(500)
                    .body("Error obteniendo salas");
        }
    }
    // ==========================================
    // DETALLE SALA
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getSala(
            @PathVariable Long id) {

        return salaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    // ==========================================
    // CERRAR SALA
    // ==========================================

    @PostMapping("/cerrar/{id}")
    public ResponseEntity<?> cerrarSala(
            @PathVariable Long id) {

        return salaRepository.findById(id)
                .map(sala -> {

                    sala.setEstado("CERRADA");

                    salaRepository.save(sala);

                    return ResponseEntity.ok(
                            "Sala cerrada"
                    );
                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    // ==========================================
    // CAMBIAR MODO
    // ==========================================

    @PostMapping("/modo/{id}")
    public ResponseEntity<?> cambiarModo(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {

        return salaRepository.findById(id)
                .map(sala -> {

                    sala.setModo(
                            payload.get("modo")
                    );

                    salaRepository.save(sala);

                    return ResponseEntity.ok(sala);
                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    // ==========================================
    // AGREGAR REGALOS
    // ==========================================

    @PostMapping("/{id}/regalos")
    public ResponseEntity<?> agregarRegalos(
            @PathVariable Long id,
            @RequestBody Map<String, Long> payload) {

        Long cantidad =
                payload.getOrDefault(
                        "cantidad",
                        0L
                );

        salaService.agregarRegalos(
                id,
                cantidad
        );

        return ResponseEntity.ok(
                "Regalos agregados"
        );
    }

    // ==========================================
    // AGREGAR TIEMPO
    // ==========================================

    @PostMapping("/{id}/tiempo")
    public ResponseEntity<?> agregarTiempo(
            @PathVariable Long id,
            @RequestBody Map<String, Long> payload) {

        Long minutos =
                payload.getOrDefault(
                        "minutos",
                        0L
                );

        salaService.agregarTiempo(
                id,
                minutos
        );

        return ResponseEntity.ok(
                "Tiempo agregado"
        );
    }

    // ==========================================
    // INFORMACIÓN DEL NIVEL
    // ==========================================

    @GetMapping("/{id}/nivel")
    public ResponseEntity<?> getNivel(
            @PathVariable Long id) {

        return salaRepository.findById(id)
                .map(sala -> ResponseEntity.ok(
                        Map.of(
                                "nivel", sala.getNivel(),
                                "asientos", sala.getCantidadAsientos(),
                                "puntos", sala.getPuntosSala(),
                                "minutos", sala.getMinutosActivos()
                        )
                ))
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }
    
    @PostMapping("/asientos/ocupar")
    public ResponseEntity<?> ocuparAsiento(@RequestBody Map<String, Object> payload) {
        try {
            // Obtenemos los valores del payload
            Long salaId = Long.valueOf(payload.get("salaId").toString());
            Integer asientoNumero = Integer.valueOf(payload.get("asientoNumero").toString());
            Long usuarioId = Long.valueOf(payload.get("usuarioId").toString());
            String usuarioNombre = payload.get("usuarioNombre") != null ? payload.get("usuarioNombre").toString() : "Invitado";
            String fotoPerfil = (payload.get("fotoPerfil") != null) ? payload.get("fotoPerfil").toString() : "assets/img/default.png";

            // LOG DE DEPURACIÓN CRÍTICO
            System.out.println("DEBUG: Intentando ocupar salaId=" + salaId + " asientoNumero=" + asientoNumero);

            // BUSQUEDA
            SalaAsiento asiento = asientoRepository.findBySalaIdAndNumeroAsiento(salaId, asientoNumero).orElse(null);
            
            if (asiento == null) {
                // SI ENTRA AQUÍ, ES PORQUE NO EXISTE EL REGISTRO 0 EN LA TABLA PARA ESA SALA
                System.err.println("ERROR: No se encontró el asiento " + asientoNumero + " para la sala " + salaId);
                return ResponseEntity.status(404).body("Asiento " + asientoNumero + " no encontrado en la base de datos.");
            }

            // Si ya está ocupado por otro, bloqueamos
            if (Boolean.TRUE.equals(asiento.getOcupado()) && !usuarioId.equals(asiento.getUsuarioId())) {
                return ResponseEntity.status(400).body("El asiento está ocupado por otro usuario");
            }

            // Asignación de datos
            asiento.setUsuarioId(usuarioId);
            asiento.setUsuarioNombre(usuarioNombre);
            asiento.setUsuarioFoto(fotoPerfil); 
            asiento.setMicroActivo(true);
            asiento.setOcupado(true);

            asientoRepository.save(asiento);
            
            System.out.println("DEBUG - Asiento ocupado con éxito: " + asientoNumero + " por " + usuarioNombre);

            return ResponseEntity.ok(asiento);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno al ocupar asiento: " + e.getMessage());
        }
    }
    @PostMapping("/asientos/liberar")
    public ResponseEntity<?> liberarAsiento(
            @RequestBody Map<String, Object> payload) {

        Long salaId =
                Long.valueOf(payload.get("salaId").toString());

        Integer asientoNumero =
                Integer.valueOf(payload.get("asientoNumero").toString());

        SalaAsiento asiento =
                asientoRepository
                        .findBySalaIdAndNumeroAsiento(
                                salaId,
                                asientoNumero
                        )
                        .orElse(null);

        if (asiento != null) {

            asiento.setUsuarioId(null);
            asiento.setUsuarioNombre(null);
            asiento.setUsuarioFoto(null);

            asiento.setMicroActivo(false);
            asiento.setOcupado(false);

            asientoRepository.save(asiento);
        }

        return ResponseEntity.ok("Asiento liberado");
    }

    @GetMapping("/asientos/{salaId}")
    public ResponseEntity<List<SalaAsiento>> getAsientos(@PathVariable Long salaId) {
        List<SalaAsiento> asientos = asientoRepository.findBySalaId(salaId);
        return ResponseEntity.ok(asientos);
    }
    
    @PostMapping("/asientos/microfono")
    public ResponseEntity<?> cambiarMicrofono(
            @RequestBody Map<String, Object> payload) {

        Long salaId =
                Long.valueOf(payload.get("salaId").toString());

        Integer asientoNumero =
                Integer.valueOf(
                        payload.get("asientoNumero").toString()
                );

        SalaAsiento asiento =
                asientoRepository
                        .findBySalaIdAndNumeroAsiento(
                                salaId,
                                asientoNumero
                        )
                        .orElse(null);

        if (asiento == null) {
            return ResponseEntity
                    .status(404)
                    .body("Asiento no encontrado");
        }

        asiento.setMicroActivo(
                !asiento.getMicroActivo()
        );

        asientoRepository.save(asiento);

        return ResponseEntity.ok(asiento);
    }
    
    @PostMapping("/asientos/expulsar")
    public ResponseEntity<?> expulsarAsiento(
            @RequestBody Map<String, Object> payload) {

        Long salaId =
                Long.valueOf(payload.get("salaId").toString());

        Integer asientoNumero =
                Integer.valueOf(
                        payload.get("asientoNumero").toString()
                );

        SalaAsiento asiento =
                asientoRepository
                        .findBySalaIdAndNumeroAsiento(
                                salaId,
                                asientoNumero
                        )
                        .orElse(null);

        if (asiento == null) {
            return ResponseEntity
                    .status(404)
                    .body("Asiento no encontrado");
        }

        asiento.setUsuarioId(null);
        asiento.setUsuarioNombre(null);
        asiento.setUsuarioFoto(null);

        asiento.setMicroActivo(false);
        asiento.setOcupado(false);

        asientoRepository.save(asiento);

        return ResponseEntity.ok("Usuario expulsado");
    }
    
    @PostMapping("/asientos/limpiar-usuario")
    public ResponseEntity<?> limpiarAsientoPorUsuario(@RequestBody Map<String, Object> payload) {
        Long usuarioId = Long.valueOf(payload.get("usuarioId").toString());
        
        // Busca todos los asientos que tenga este usuario (por si acaso tiene más de uno)
        List<SalaAsiento> asientos = asientoRepository.findByUsuarioId(usuarioId);
        
        for (SalaAsiento asiento : asientos) {
            asiento.setUsuarioId(null);
            asiento.setUsuarioNombre(null);
            asiento.setUsuarioFoto(null);
            asiento.setMicroActivo(false);
            asiento.setOcupado(false);
            asientoRepository.save(asiento);
        }
        return ResponseEntity.ok("Asiento(s) limpiado(s)");
    }
    
    
}