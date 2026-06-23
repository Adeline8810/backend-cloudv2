package com.demo.adeline.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty; // ¡Importante!

@Entity
@Table(name = "sala_asientos")
public class SalaAsiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "sala_id")
    @JsonProperty("salaId")
    private Long salaId;

    @Column(name = "numero_asiento")
    @JsonProperty("numeroAsiento")
    private Integer numeroAsiento;
    
    @Column(name = "usuario_id")
    @JsonProperty("usuarioId")
    private Long usuarioId;

    @Column(name = "ocupado")
    @JsonProperty("ocupado")
    private Boolean ocupado = false;
    
    @Column(name = "usuario_nombre")
    @JsonProperty("usuarioNombre")
    private String usuarioNombre;

    @Column(name = "usuario_foto")
    @JsonProperty("usuarioFoto")
    private String usuarioFoto;
    
    @Column(name = "micro_activo")
    @JsonProperty("microActivo")
    private Boolean microActivo = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSalaId() {
		return salaId;
	}

	public void setSalaId(Long salaId) {
		this.salaId = salaId;
	}

	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}

	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Boolean getOcupado() {
		return ocupado;
	}

	public void setOcupado(Boolean ocupado) {
		this.ocupado = ocupado;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	public String getUsuarioFoto() {
		return usuarioFoto;
	}

	public void setUsuarioFoto(String usuarioFoto) {
		this.usuarioFoto = usuarioFoto;
	}

	public Boolean getMicroActivo() {
		return microActivo;
	}

	public void setMicroActivo(Boolean microActivo) {
		this.microActivo = microActivo;
	}

    // ... mantén tus getters y setters aquí abajo
    
    
}