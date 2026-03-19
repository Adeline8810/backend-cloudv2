package com.demo.adeline.model;

public class RespuestaAmigoDTO {
    private String pregunta;
    private String respuesta;
    private String fotoUrl; // ✅ Obligatorio para que coincida con la Query

    public RespuestaAmigoDTO() {}

    // ✅ Este constructor es el que Hibernate busca desesperadamente
    public RespuestaAmigoDTO(String pregunta, String respuesta, String fotoUrl) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.fotoUrl = fotoUrl;
    }

    // Getters y Setters...
    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}