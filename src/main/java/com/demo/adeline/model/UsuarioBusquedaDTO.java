package com.demo.adeline.model;

//UsuarioBusquedaDTO.java
public class UsuarioBusquedaDTO {
 private Long id;
 private String nombre;
 private String email;
 private String fotoPerfil; // Para mostrar su miniatura en la lista

 public UsuarioBusquedaDTO(Long id, String nombre, String email, String fotoPerfil) {
     this.id = id;
     this.nombre = nombre;
     this.email = email;
     this.fotoPerfil = fotoPerfil;
 }
 // Getters y Setters...
}