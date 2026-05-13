package com.demo.adeline.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comentario_likes")
public class ComentarioLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long comentarioId;
    private Long usuarioId;

    public ComentarioLike() {}

    public ComentarioLike(Long comentarioId, Long usuarioId) {
        this.comentarioId = comentarioId;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getComentarioId() { return comentarioId; }
    public void setComentarioId(Long comentarioId) { this.comentarioId = comentarioId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}