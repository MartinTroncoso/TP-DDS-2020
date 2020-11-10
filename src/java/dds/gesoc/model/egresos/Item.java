package dds.gesoc.model.egresos;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;

import javax.persistence.Embedded;
import javax.persistence.Entity;


@Entity
public class Item extends EntidadPersistente{
	
	private String descripcion;
	
	@Embedded
	private ValorMonetario valorItem;
	
	public Item() {
	}
	
	public Item(String descripcion, ValorMonetario valor) {
		this.descripcion = descripcion;
		this.valorItem = valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public double getMonto() {
		return this.valorItem.getMonto();
	}
	
	public Moneda getMoneda() {
		return this.valorItem.getMoneda();
	}
}
