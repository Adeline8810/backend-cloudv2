package com.demo.adeline.model;

public class RespuestaAmigoDTO {
    private String pregunta;
    private String respuesta;
    private String fotoUrl; // ✅ Añadido

    // Constructor vacío (siempre es bueno tenerlo)
    public RespuestaAmigoDTO() {}

    // ✅ CONSTRUCTOR ACTUALIZADO: Debe recibir 3 parámetros en este orden
    public RespuestaAmigoDTO(String pregunta, String respuesta, String fotoUrl) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.fotoUrl = fotoUrl;
    }

    // Getters y Setters
    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public String getFotoUrl() { return fotoUrl; } // ✅ Añadido
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}