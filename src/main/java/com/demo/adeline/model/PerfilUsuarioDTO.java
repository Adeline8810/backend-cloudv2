package com.demo.adeline.model;

import java.util.List;

public class PerfilUsuarioDTO {
    private Long id;
    private String idPublico;
    private String nombre;
    private String fotoPerfil;
    private String fotoPortada;
    private String bio;
    private String signo;
    private String sexo;
    
    private long totalFollowers;
    private long totalFollowing;
    private long totalGiftsRecibidos; // Suma de puntos en la tabla regalos
    
    private List<String> insignias;
    private List<String> escudos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdPublico() {
		return idPublico;
	}
	public void setIdPublico(String idPublico) {
		this.idPublico = idPublico;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	public String getFotoPortada() {
		return fotoPortada;
	}
	public void setFotoPortada(String fotoPortada) {
		this.fotoPortada = fotoPortada;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getSigno() {
		return signo;
	}
	public void setSigno(String signo) {
		this.signo = signo;
	}
		
	
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public long getTotalFollowers() {
		return totalFollowers;
	}
	public void setTotalFollowers(long totalFollowers) {
		this.totalFollowers = totalFollowers;
	}
	public long getTotalFollowing() {
		return totalFollowing;
	}
	public void setTotalFollowing(long totalFollowing) {
		this.totalFollowing = totalFollowing;
	}
	public long getTotalGiftsRecibidos() {
		return totalGiftsRecibidos;
	}
	public void setTotalGiftsRecibidos(long totalGiftsRecibidos) {
		this.totalGiftsRecibidos = totalGiftsRecibidos;
	}
	public List<String> getInsignias() {
		return insignias;
	}
	public void setInsignias(List<String> insignias) {
		this.insignias = insignias;
	}
	public List<String> getEscudos() {
		return escudos;
	}
	public void setEscudos(List<String> escudos) {
		this.escudos = escudos;
	}
    
    // Constructores y Getters...
    
    
    
    
    
}
