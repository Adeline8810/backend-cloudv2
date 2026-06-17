package com.demo.adeline.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils; // Import necesario para subir archivos
import com.demo.adeline.model.Insignia;
import com.demo.adeline.repository.InsigniaRepository;
import com.demo.adeline.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import necesario
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Import necesario

import java.util.List;
import java.util.Map; // Import necesario



@RestController
@RequestMapping("/api/insignias")
public class InsigniaController {

    @Autowired
    private InsigniaRepository insigniaRepo;
    
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    private final Cloudinary cloudinary;
    
    
    @Autowired
    public InsigniaController(
            Cloudinary cloudinary
           ) {
// <-- INICIALÍZALO AQUÍ
this.cloudinary = cloudinary;

}
    

    // 1. Listar todas
    @GetMapping
    public List<Insignia> listar() {
        return insigniaRepo.findAll();
    }

    // 2. Crear
    @PostMapping
    public Insignia crear(@RequestBody Insignia insignia) {
        return insigniaRepo.save(insignia);
    }

    // 3. Editar
    @PutMapping("/{id}")
    public ResponseEntity<Insignia> editar(@PathVariable Long id, @RequestBody Insignia detalles) {
        return insigniaRepo.findById(id).map(insignia -> {
            insignia.setNombre(detalles.getNombre());
            insignia.setIconoUrl(detalles.getIconoUrl());
            return ResponseEntity.ok(insigniaRepo.save(insignia));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    
    
    @PostMapping("/upload")
    public ResponseEntity<String> subirInsignia(
        @RequestParam("file") MultipartFile file,
        @RequestParam("nombre") String nombre,
        @RequestParam("nombrePresent") String nombrePresent
    ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            // CORRECCIÓN AQUÍ: Usamos el objeto 'cloudinary' inyectado
            // Esto sube el archivo a Cloudinary y obtiene el mapa de respuesta
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            // 3. Crear el objeto Insignia
            Insignia nuevaInsignia = new Insignia();
            nuevaInsignia.setNombre(nombre);
            nuevaInsignia.setNombrePresent(nombrePresent);
            nuevaInsignia.setIconoUrl(imageUrl);

            // 4. Guardar en base de datos
            insigniaRepo.save(nuevaInsignia);

            return ResponseEntity.ok("Insignia creada exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al guardar la insignia: " + e.getMessage());
        }
    }

    // 4. Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!insigniaRepo.existsById(id)) return ResponseEntity.notFound().build();
        insigniaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 5. ASIGNAR (Mantenemos tu lógica original)
    @PostMapping("/{insigniaId}/asignar/{usuarioId}")
    public ResponseEntity<?> asignarInsignia(@PathVariable Long insigniaId, @PathVariable Long usuarioId) {
        return usuarioRepo.findById(usuarioId).map(usuario -> {
            return insigniaRepo.findById(insigniaId).map(insignia -> {
                if (!usuario.getInsignias().contains(insignia)) {
                    usuario.getInsignias().add(insignia);
                    usuarioRepo.save(usuario);
                }
                return ResponseEntity.ok("Insignia asignada correctamente");
            }).orElse(ResponseEntity.notFound().build());
        }).orElse(ResponseEntity.notFound().build());
    }
    
    
    
    
    
}