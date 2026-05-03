package com.demo.adeline.controller;

import com.demo.adeline.model.Usuario;
import com.demo.adeline.model.UsuarioBusquedaDTO;
import com.demo.adeline.repository.UsuarioRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Los que te faltaban o son nuevos:
import com.cloudinary.Cloudinary;           // <--- ESTE ES EL QUE TE DABA ERROR
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;                 // <--- Añade este para los errores de subida

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.demo.adeline.model.Mensaje;
import com.demo.adeline.model.PerfilUsuarioDTO;
import com.demo.adeline.repository.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map; // Para el @RequestBody Map<String, String>
import org.springframework.http.HttpStatus; // Para HttpStatus.UNAUTHORIZED
import com.google.firebase.auth.FirebaseAuthException;


@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {

    private final UsuarioRepository repo;
    private final Cloudinary cloudinary;
    private final MensajeRepository mensajeRepo;
    
    

    public UsuarioController(UsuarioRepository repo,Cloudinary cloudinary, MensajeRepository mensajeRepo) {
        this.repo = repo;
        this.cloudinary = cloudinary;
        this.mensajeRepo = mensajeRepo;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario u) {
        if (u.getUsername() == null || u.getEmail() == null || u.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (repo.findByUsername(u.getUsername()).isPresent() || repo.findByEmail(u.getEmail()).isPresent()) {
            return ResponseEntity.status(409).build(); // conflict
        }

        Usuario saved = repo.save(u);
        return ResponseEntity.created(URI.create("/api/usuarios/" + saved.getId())).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario u) {
        if (u.getUsername() == null || u.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> found = repo.findByUsernameAndPassword(u.getUsername(), u.getPassword());
        return found.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getOne(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario u) {
        return repo.findById(id).map(existing -> {
            existing.setNombre(u.getNombre());
            existing.setEmail(u.getEmail());
            if (u.getPassword() != null && !u.getPassword().isEmpty()) {
                existing.setPassword(u.getPassword());
            }
            return ResponseEntity.ok(repo.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
 // ESTE ES EL MÉTODO QUE ME PREGUNTASTE
    @GetMapping("/buscar-usuarios")
    public List<UsuarioBusquedaDTO> buscarUsuarios(@RequestParam String nombre) {
        return repo.buscarUsuariosPorTermino(nombre); 
    }
    
    
 // Este método usa el findByUsername que ya tienes en el repositorio
    @GetMapping("/monedas/{username}")
    public ResponseEntity<Usuario> obtenerDatosRecarga(@PathVariable String username) {
        return repo.findByUsername(username)
                .map(usuario -> {
                    // Limpiamos el password por seguridad antes de enviarlo al Front
                    usuario.setPassword(null);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/perfil/{username}")
    public ResponseEntity<Usuario> obtenerPerfilPublico(@PathVariable String username) {
        return repo.findByUsername(username)
                .map(usuario -> {
                    // Seteamos a null datos sensibles antes de enviar
                    usuario.setPassword(null);
                    // Aquí podrías setear a null el email si quieres que sea privado
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Servidor despertado correctamente");
    }
    
    @PostMapping("/upload-foto")
    public ResponseEntity<String> uploadFotoPerfil(@RequestParam("file") MultipartFile file, @RequestParam("usuarioId") Long usuarioId) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }

        // A. Subir a Cloudinary
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", "perfiles_usuarios",
            "resource_type", "image"
        ));

        String urlCloudinary = uploadResult.get("secure_url").toString();

        // B. Guardar en la tabla Usuario
        return repo.findById(usuarioId).map(usuario -> {
            usuario.setFotoUrl(urlCloudinary); // Asegúrate de que Usuario.java tenga este campo
            repo.save(usuario);
            return ResponseEntity.ok(urlCloudinary);
        }).orElseGet(() -> ResponseEntity.status(404).body("Usuario no encontrado"));
    }
    
    
    @GetMapping("/chat/historial")
    public List<Mensaje> obtenerHistorial(@RequestParam Long emisorId, @RequestParam Long receptorId) {
        return mensajeRepo.obtenerHistorial(emisorId, receptorId);
    }
    
    
 // POR ESTE NUEVO (Cambiamos la ruta a /perfil-completo/ para que no choque):
    @GetMapping("/perfil-completo/{idPublico}")
    public ResponseEntity<?> obtenerPerfilCompleto(@PathVariable String idPublico) {
        return repo.findByIdPublico(idPublico)
            .map(user -> {
                PerfilUsuarioDTO perfil = new PerfilUsuarioDTO();
                perfil.setId(user.getId());
                perfil.setIdPublico(user.getIdPublico());
                perfil.setNombre(user.getNombre());
                perfil.setFotoPerfil(user.getFotoUrl()); // Usamos fotoUrl que ya tienes
                perfil.setFotoPortada(user.getFotoPortada());
                perfil.setBio(user.getBio());
                perfil.setSigno(user.getSigno());
                perfil.setSexo(user.getSexo());
                
                // Mapeo de insignias y escudos
                perfil.setInsignias(user.getInsignias().stream().map(i -> i.getIconoUrl()).toList());
                perfil.setEscudos(user.getEscudos().stream().map(e -> e.getIconoUrl()).toList());
                
                perfil.setTotalFollowers(0); 
                perfil.setTotalFollowing(0);
                
                return ResponseEntity.ok(perfil);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/completar-perfil/{id}")
    public ResponseEntity<?> completarPerfil(@PathVariable Long id, @RequestBody Usuario datos) {
        return repo.findById(id).map(user -> {
            if (datos.getBio() != null) user.setBio(datos.getBio());
            if (datos.getSigno() != null) user.setSigno(datos.getSigno());
            if (datos.getSexo() != null) user.setSexo(datos.getSexo());
            if (datos.getCumpleanos() != null) user.setCumpleanos(datos.getCumpleanos());
            
            repo.save(user);
            return ResponseEntity.ok("Perfil actualizado correctamente");
        }).orElse(ResponseEntity.notFound().build());
    }
    
    
    @PostMapping("/auth/firebase")
    public ResponseEntity<?> loginConFirebase(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            
            // Verificamos el token con Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            
            // CORRECCIÓN AQUÍ: Usamos .orElse(null) porque el repo devuelve Optional
            Usuario usuario = repo.findByFirebaseUid(uid).orElse(null);
            
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setFirebaseUid(uid);
                usuario.setEmail(decodedToken.getEmail());
                usuario.setUsername(decodedToken.getEmail());
                usuario.setPassword("FIREBASE_AUTH");
                usuario.setNombre(decodedToken.getName());
                usuario.setMonedas(0); 
                repo.save(usuario);
            }
            
            return ResponseEntity.ok(usuario);
            
        } catch (FirebaseAuthException e) {
            // Asegúrate de que el import de HttpStatus esté arriba
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de seguridad: " + e.getMessage());
        }
    }
   
    
    
}
