package dds.gesoc.model.egresos;

import javax.persistence.*;

import dds.gesoc.entities.EntidadPersistente;

@Entity
@Table(name="documento")
public class Documento extends EntidadPersistente{
	
	@Column
	private String tipo;
	
	@Column
	private int numero;
	
	public Documento(){}
	
	public Documento(String tipo, int numero) {
		this.tipo = tipo;
		this.numero = numero;
	}
}
