package com.demo.adeline.model;
import jakarta.persistence.*;




@Entity
@Table(name = "album_fotos")
public class AlbumFoto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private String urlFoto;
    private boolean esPortada = false; // true si es la que se muestra en el perfil
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
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public boolean isEsPortada() {
		return esPortada;
	}
	public void setEsPortada(boolean esPortada) {
		this.esPortada = esPortada;
	}
    
    
    
    
    
    
}