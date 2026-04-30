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
    private String urlAudio; // Aquí guardaremos el link de Cloudinary
    

    @Column(columnDefinition = "TEXT") 
    private String letraJson; // El JSON que generó el "TAP"

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
    
    
    
    
    
    
    
    
}