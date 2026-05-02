package com.demo.adeline.model;

import java.time.LocalDateTime;

public class ComentarioDTO {
    private Long id;
    private String contenido;
    private LocalDateTime fecha;
    private Long usuarioId;
    private String nombreUsuario; // <-- Lo que necesitamos
    private String usuarioFoto;   // <-- Lo que necesitamos
    private Integer likesCount;

    // Constructor para que sea fácil de mapear
    public ComentarioDTO(Long id, String contenido, LocalDateTime fecha, Long usuarioId, 
                         String nombreUsuario, String usuarioFoto, Integer likesCount) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.usuarioFoto = usuarioFoto;
        this.likesCount = likesCount;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getUsuarioFoto() {
		return usuarioFoto;
	}

	public void setUsuarioFoto(String usuarioFoto) {
		this.usuarioFoto = usuarioFoto;
	}

	public Integer getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Integer likesCount) {
		this.likesCount = likesCount;
	}

    
    
    // Getters y Setters...
}