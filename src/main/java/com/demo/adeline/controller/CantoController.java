package com.demo.adeline.controller;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;
import com.demo.adeline.model.Canto;
import com.demo.adeline.repository.CantoRepository;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cantos")

public class CantoController {

    @Autowired
    private Cloudinary cloudinary; // El que ya configuraste para tus fotos/videos
    @Autowired
    private CantoRepository cantoRepository;

    @PostMapping("/subir")
    public ResponseEntity<?> subirCanto(
        @RequestParam("archivo") MultipartFile archivo,
        @RequestParam("usuarioId") Long usuarioId,
        @RequestParam(value = "tipo", defaultValue = "audio") String tipo) { 
        try {
            // 1. Configuración de Cloudinary
        	System.out.println("DEBUG: El tipo recibido es: " + tipo);
            Map options = ObjectUtils.asMap(
                "resource_type", "video", 
                "folder", "karaoke_covers"
            );

            Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), options);
            String urlFinal = uploadResult.get("secure_url").toString();

            // 2. Guardar en la base de datos según el tipo
            Canto nuevoCanto = new Canto();
            nuevoCanto.setUsuarioId(usuarioId);
            nuevoCanto.setTipo(tipo); // 'audio' o 'video'    
            
            // ASIGNAR ESTADO ACTIVO POR DEFECTO
            nuevoCanto.setEstado("ACTIVO");
            
         // AQUÍ ESTÁ LA LÓGICA QUE DEBE FUNCIONAR:
            if ("video".equalsIgnoreCase(tipo.trim())) {
                nuevoCanto.setUrlVideo(urlFinal);
                nuevoCanto.setUrlAudio(""); // Usamos cadena vacía en vez de null
            } else {
                nuevoCanto.setUrlAudio(urlFinal);
                nuevoCanto.setUrlVideo(""); // Usamos cadena vacía en vez de null
            }
            
            
            
            cantoRepository.save(nuevoCanto);
            return ResponseEntity.ok(nuevoCanto);

        } catch (Exception e) {
   
          	e.printStackTrace(); 
            
            // 2. Esto te da una pista en la consola del navegador
            return ResponseEntity.status(500).body("Error al subir: " + e.toString());
       
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Canto>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        // Trae solo los cantos del usuario cuyo estado NO sea 'ELIMINADO'
        return ResponseEntity.ok(cantoRepository.findByUsuarioIdAndEstadoNot(usuarioId, "ELIMINADO"));
    }
    
    @GetMapping("/todos")
    public ResponseEntity<List<Canto>> listarTodosLosCantos() {
        // Trae todos los cantos globales de la app que NO estén eliminados
        return ResponseEntity.ok(cantoRepository.findByEstadoNot("ELIMINADO"));
    }
    
    
 // ... Tus otros endpoints (subir, obtenerPorUsuario, etc.)

    @PutMapping("/{id}/eliminar-logica")
    public ResponseEntity<?> eliminarCantoLogica(@PathVariable Long id) {
        try {
            // 1. Buscar el canto por su ID usando tu repositorio
            Canto canto = cantoRepository.findById(id).orElse(null);

            if (canto == null) {
                return ResponseEntity.status(404).body("No se encontró el cover con el ID: " + id);
            }

            // 2. Aplicar la eliminación lógica cambiando el estado
            canto.setEstado("ELIMINADO");

            // 3. Guardar el registro actualizado en Supabase
            cantoRepository.save(canto);

            // Devolvemos una respuesta exitosa con un mapa con el mensaje
            return ResponseEntity.ok(Map.of(
                "mensaje", "La cobertura ha sido marcada como eliminada correctamente",
                "id", id
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }
    
    @GetMapping("/recientes")
    public List<Canto> getRecientes() {
        // Asegúrate de que este método exista en tu repositorio
    	return cantoRepository.findByEstadoOrderByFechaCreacionDesc("ACTIVO");
    }
    
}