package dds.gesoc.model.geografia;

import javax.persistence.Entity;

import db.PersistentEntity;

@Entity
public class Moneda extends PersistentEntity{

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
	
}
