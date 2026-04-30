package com.demo.adeline.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String username;

    private String nombre;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;
    
    private Integer monedas;
    
    private String fotoUrl;
    
    
    //nuevos
    private String idPublico;
    private String fotoPortada;
    private String signo;
    private LocalDate cumpleanos;
    private String bio;
    private String sexo;
    
    

    public Usuario() {}

    public Usuario(String username, String nombre, String email, String password,int monedas,
    		String idPublico,String fotoPortada, String signo,LocalDate cumpleanos,String bio,String sexo) {
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.monedas = monedas;
        this.idPublico=idPublico;
        this.fotoPortada=fotoPortada;
        this.signo=signo;
        this.cumpleanos=cumpleanos;
        this.bio=bio;
        this.sexo=sexo;
    }
    
    
    @ManyToMany
    @JoinTable(name = "usuario_insignias")
    private Set<Insignia> insignias= new HashSet<>();

    @ManyToMany
    @JoinTable(name = "usuario_escudos")
    private Set<Escudo> escudos =new HashSet<>();

    // Getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Integer getMonedas() { return monedas; }
    public void setMonedas(Integer monedas) { this.monedas = monedas; }
    
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

	public String getIdPublico() {
		return idPublico;
	}

	public void setIdPublico(String idPublico) {
		this.idPublico = idPublico;
	}

	public String getFotoPortada() {
		return fotoPortada;
	}

	public void setFotoPortada(String fotoPortada) {
		this.fotoPortada = fotoPortada;
	}

	public String getSigno() {
		return signo;
	}

	public void setSigno(String signo) {
		this.signo = signo;
	}

	public LocalDate getCumpleanos() {
		return cumpleanos;
	}

	public void setCumpleanos(LocalDate cumpleanos) {
		this.cumpleanos = cumpleanos;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Set<Insignia> getInsignias() {
		return insignias;
	}

	public void setInsignias(Set<Insignia> insignias) {
		this.insignias = insignias;
	}

	public Set<Escudo> getEscudos() {
		return escudos;
	}

	public void setEscudos(Set<Escudo> escudos) {
		this.escudos = escudos;
	}
    
    
    
    
}
