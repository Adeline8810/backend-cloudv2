package com.demo.adeline.model;





public class RespuestaAmigoDTO {
    private String pregunta;
    private String respuesta;

    public RespuestaAmigoDTO(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }
    // Getters y Setters...

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
    
    
    
}