package com.demo.adeline.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "escudos")
public class Escudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "icono_url")
    private String iconoUrl;

    @ManyToMany(mappedBy = "escudos")
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();

    public Escudo() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIconoUrl() { return iconoUrl; }
    public void setIconoUrl(String iconoUrl) { this.iconoUrl = iconoUrl; }
    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }
}