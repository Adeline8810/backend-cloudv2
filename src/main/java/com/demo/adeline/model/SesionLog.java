package com.demo.adeline.model;


import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sesiones_logs", schema = "public")
public class SesionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "ip_address")
    private String ipAddress;

    private String pais;
    private String ciudad;
    private String dispositivo;
    private String navegador;

    @Column(name = "fecha_ingreso", insertable = false, updatable = false)
    private ZonedDateTime fechaIngreso;

    @Column(name = "url_entrada")
    private String urlEntrada;
    
    
    private Double latitud;
    private Double longitud;

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    
    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String dispositivo() { return dispositivo; }
    public void setDispositivo(String dispositivo) { this.dispositivo = dispositivo; }
    
    public String getNavegador() { return navegador; }
    public void setNavegador(String navegador) { this.navegador = navegador; }
    
    public ZonedDateTime getFechaIngreso() { return fechaIngreso; }
    
    public String getUrlEntrada() { return urlEntrada; }   
    public void setUrlEntrada(String urlEntrada) { this.urlEntrada = urlEntrada; }
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
    
    
    
    
    
    
    
}
