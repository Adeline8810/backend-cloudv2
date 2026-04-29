package com.demo.adeline.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "cantos")
public class Canto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlAudio;
    private Long usuarioId;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrlAudio() {
		return urlAudio;
	}
	public void setUrlAudio(String urlAudio) {
		this.urlAudio = urlAudio;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

    // Getters y Setters
    
    
    
    
}