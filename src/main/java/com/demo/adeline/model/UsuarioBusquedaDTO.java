package com.demo.adeline.model;

public class UsuarioBusquedaDTO {
    private Long id;
    private String nombre;
    private String email;
    private String username;

    // Constructor vacío
    public UsuarioBusquedaDTO() {}

    // Constructor con TODOS los campos (Este es el que usa la Query)
    public UsuarioBusquedaDTO(Long id, String nombre, String email, String username) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.username = username;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}