package com.demo.adeline.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "insignias")
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String nombrePresent;
    

    @Column(name = "icono_url")
    private String iconoUrl;

    @ManyToMany(mappedBy = "insignias")
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();

    // Constructor vacío obligatorio para JPA
    public Insignia() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIconoUrl() { return iconoUrl; }
    public void setIconoUrl(String iconoUrl) { this.iconoUrl = iconoUrl; }
    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }

	public String getNombrePresent() {
		return nombrePresent;
	}

	public void setNombrePresent(String nombrePresent) {
		this.nombrePresent = nombrePresent;
	}
    
    
}