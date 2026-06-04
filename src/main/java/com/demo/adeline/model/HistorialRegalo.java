package com.demo.adeline.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_regalos")
public class HistorialRegalo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idRegalador;
    private Long idDestinatario;
    private Long idRegalo;
    private LocalDateTime fechaHora = LocalDateTime.now();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdRegalador() { return idRegalador; }
    public void setIdRegalador(Long idRegalador) { this.idRegalador = idRegalador; }
    public Long getIdDestinatario() { return idDestinatario; }
    public void setIdDestinatario(Long idDestinatario) { this.idDestinatario = idDestinatario; }
    public Long getIdRegalo() { return idRegalo; }
    public void setIdRegalo(Long idRegalo) { this.idRegalo = idRegalo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}