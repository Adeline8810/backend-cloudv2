package com.demo.adeline.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes") // Este nombre debe ser igual al que pusiste en Supabase
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    
    @Column(name = "emisor_id")
    private Long emisorId;

    @Column(name = "receptor_id")
    private Long receptorId;

    private String hora;

    // La fecha se genera automáticamente en la base de datos
    @Column(insertable = false, updatable = false)
    private LocalDateTime fecha;

    // --- CONSTRUCTORES ---
    public Mensaje() {}

    // --- GETTERS Y SETTERS (Importantes para que Java pueda leer los datos) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public Long getEmisorId() { return emisorId; }
    public void setEmisorId(Long emisorId) { this.emisorId = emisorId; }

    public Long getReceptorId() { return receptorId; }
    public void setReceptorId(Long receptorId) { this.receptorId = receptorId; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}