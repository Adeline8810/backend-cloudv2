package com.demo.adeline.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salas_conversacion")
public class SalaConversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "usuarioId")
    private Long usuarioId;

    private String modo;

    private String estado;

    @Column(name = "room_name")
    private String roomName;
    
    @Column(columnDefinition = "TEXT")
    private String portadaUrl;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "nivel_sala")
    private Integer nivelSala = 1;

    @Column(name = "experiencia_sala")
    private Long experienciaSala = 0L;

    @Column(name = "max_asientos")
    private Integer maxAsientos = 8;

    @Column(name = "total_regalos")
    private Long totalRegalos = 0L;

    @Column(name = "minutos_activa")
    private Long minutosActiva = 0L;

    @Column(name = "total_usuarios")
    private Long totalUsuarios = 0L;
    

    
    private Integer nivel = 1;

    @Column(name = "puntos_sala")
    private Long puntosSala = 0L;

    @Column(name = "minutos_activos")
    private Long minutosActivos = 0L;

    @Column(name = "cantidad_asientos")
    private Integer cantidadAsientos = 4;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

 
    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

	public Integer getNivelSala() {
		return nivelSala;
	}

	public void setNivelSala(Integer nivelSala) {
		this.nivelSala = nivelSala;
	}

	public Long getExperienciaSala() {
		return experienciaSala;
	}

	public void setExperienciaSala(Long experienciaSala) {
		this.experienciaSala = experienciaSala;
	}

	public Integer getMaxAsientos() {
		return maxAsientos;
	}

	public void setMaxAsientos(Integer maxAsientos) {
		this.maxAsientos = maxAsientos;
	}

	public Long getTotalRegalos() {
		return totalRegalos;
	}

	public void setTotalRegalos(Long totalRegalos) {
		this.totalRegalos = totalRegalos;
	}

	public Long getMinutosActiva() {
		return minutosActiva;
	}

	public void setMinutosActiva(Long minutosActiva) {
		this.minutosActiva = minutosActiva;
	}

	public Long getTotalUsuarios() {
		return totalUsuarios;
	}

	public void setTotalUsuarios(Long totalUsuarios) {
		this.totalUsuarios = totalUsuarios;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Long getPuntosSala() {
		return puntosSala;
	}

	public void setPuntosSala(Long puntosSala) {
		this.puntosSala = puntosSala;
	}

	public Long getMinutosActivos() {
		return minutosActivos;
	}

	public void setMinutosActivos(Long minutosActivos) {
		this.minutosActivos = minutosActivos;
	}

	public Integer getCantidadAsientos() {
		return cantidadAsientos;
	}

	public void setCantidadAsientos(Integer cantidadAsientos) {
		this.cantidadAsientos = cantidadAsientos;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getPortadaUrl() {
		return portadaUrl;
	}

	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}


    
    
    
}