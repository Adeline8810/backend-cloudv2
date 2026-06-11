package com.demo.adeline.model;
import jakarta.persistence.*;

@Entity
@Table(name = "catalogo_regalos")
public class CatalogoRegalos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo; // 'MONEDA' o 'GRATIS'
    private String urlImagen;
    private Integer valorMonedas;
    private Integer valorEstrellas;
    private String categoria;
    @Column(name = "activo")
    private boolean activo = true;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public Integer getValorMonedas() { return valorMonedas; }
    public void setValorMonedas(Integer valorMonedas) { this.valorMonedas = valorMonedas; }
    public Integer getValorEstrellas() { return valorEstrellas; }
    public void setValorEstrellas(Integer valorEstrellas) { this.valorEstrellas = valorEstrellas; }
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
    
	
	
}
    
    
