package com.demo.adeline.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.demo.adeline.model.CatalogoRegalos;
import com.demo.adeline.model.HistorialRegalo;
import com.demo.adeline.model.RegaloRequest;
import com.demo.adeline.repository.CatalogoRegalosRepository;
import com.demo.adeline.repository.HistorialRegaloRepository;


//Los que te faltaban o son nuevos:
import com.cloudinary.Cloudinary;           // <--- ESTE ES EL QUE TE DABA ERROR
import com.cloudinary.utils.ObjectUtils;
// Importaciones de tu modelo y repositorio
       // <-- Ajusta según donde esté tu clase Regalo

@RestController
@RequestMapping("/api/regalos")

public class RegaloController {

    @Autowired
    private CatalogoRegalosRepository catalogoRepo;

    @Autowired
    private HistorialRegaloRepository historialRepo;
    

    private final Cloudinary cloudinary;


    // Este es el constructor que estabas buscando:
    @Autowired
    public RegaloController(CatalogoRegalosRepository catalogoRepo, 
            Cloudinary cloudinary
           ) {
this.catalogoRepo = catalogoRepo; // <-- INICIALÍZALO AQUÍ
this.cloudinary = cloudinary;

}
    
    
    

    // Obtener catálogo disponible
    @GetMapping("/lista")
    public List<CatalogoRegalos> listarRegalos() {
        return catalogoRepo.findAll();
    }

    // Registrar envío de regalo
    @PostMapping("/enviar")
    public String enviarRegalo(@RequestBody RegaloRequest request) {
        HistorialRegalo historial = new HistorialRegalo();
        historial.setIdRegalador(request.getRegaladorId());
        historial.setIdDestinatario(request.getDestinatarioId());
        historial.setIdRegalo(request.getIdRegalo());
        
        historialRepo.save(historial);
        return "OK";
    }
    
    
    
    @PostMapping("/upload-regalo")
    public ResponseEntity<String> uploadRegalo(@RequestParam("file") MultipartFile file, 
                                              @RequestParam("nombre") String nombre,
                                              @RequestParam("costo") Integer costo) throws IOException {
        
        // 1. Subir a Cloudinary
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", "regalos",
            "resource_type", "image"
        ));

        String urlCloudinary = uploadResult.get("secure_url").toString();

        // 2. Usamos tu clase existente: CatalogoRegalos
        CatalogoRegalos nuevoRegalo = new CatalogoRegalos();
        nuevoRegalo.setNombre(nombre);
        nuevoRegalo.setValorMonedas(costo); // Mapeamos el costo al campo valorMonedas
        nuevoRegalo.setUrlImagen(urlCloudinary);
        
        // Asumimos que tienes un repositorio llamado catalogoRegalosRepository
        catalogoRepo.save(nuevoRegalo);

        return ResponseEntity.ok(urlCloudinary);
    }
    
    
}