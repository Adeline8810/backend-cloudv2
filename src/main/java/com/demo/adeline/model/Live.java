package com.demo.adeline.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lives")
public class Live {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private String titulo;
    private String estado; // "EN_VIVO", "INACTIVO"
    private String streamKey;
    private String thumbnailUrl;
    private LocalDateTime fechaInicio = LocalDateTime.now();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getStreamKey() {
		return streamKey;
	}
	public void setStreamKey(String streamKey) {
		this.streamKey = streamKey;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

    // Getters y Setters...
    
    
    
    
}