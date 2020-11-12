package dds.gesoc.model.geografia;

import javax.persistence.Entity;

import dds.gesoc.entities.EntidadPersistente;

@Entity
public class Moneda extends EntidadPersistente{

	private String cadenaIdentificadora;
	private String descripcion;
	private String simbolo;
	
	public Moneda() {
	}
	
	public Moneda(String cadenaIdentificadora, String descripcion, String simbolo) {
		super();
		this.setCadenaIdentificadora(cadenaIdentificadora);
		this.descripcion = descripcion;
		this.simbolo = simbolo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getSimbolo() {
		return simbolo;
	}

	public String getCadenaIdentificadora() {
		return cadenaIdentificadora;
	}

	public void setCadenaIdentificadora(String cadenaIdentificadora) {
		this.cadenaIdentificadora = cadenaIdentificadora;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
}
