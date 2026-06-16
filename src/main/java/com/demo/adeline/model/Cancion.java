package com.demo.adeline.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Si usas Lombok, si no, genera Getters/Setters
public class Cancion {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String artista;
    private String urlAudio; 
    @Column(name = "letra_json", columnDefinition = "TEXT") 
    private String letraJson;
    
    @Column(name = "portada_url")
    private String portadaUrl;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "username")
    private String username;

    @Column(name = "usuario_foto")
    private String usuarioFoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getUrlAudio() {
		return urlAudio;
	}

	public void setUrlAudio(String urlAudio) {
		this.urlAudio = urlAudio;
	}

	public String getLetraJson() {
		return letraJson;
	}

	public void setLetraJson(String letraJson) {
		this.letraJson = letraJson;
	}

	public String getPortadaUrl() {
		return portadaUrl;
	}

	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsuarioFoto() {
		return usuarioFoto;
	}

	public void setUsuarioFoto(String usuarioFoto) {
		this.usuarioFoto = usuarioFoto;
	}
    
    
    
    
    
    
    
    
    
    
}