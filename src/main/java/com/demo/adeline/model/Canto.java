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
    @Column(name = "url_video") // La nueva columna
    private String urlVideo;

    private String tipo; // 'audio' o 'video'
    
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
	public String getUrlVideo() {
		return urlVideo;
	}
	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

    // Getters y Setters
    
    
    
    
}