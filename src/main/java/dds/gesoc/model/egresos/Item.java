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
		this.setDescripcion(descripcion);
		this.setValorItem(valor);
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

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ValorMonetario getValorItem() {
		return valorItem;
	}

	public void setValorItem(ValorMonetario valorItem) {
		this.valorItem = valorItem;
	}
}
