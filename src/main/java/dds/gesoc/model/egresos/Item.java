package dds.gesoc.model.egresos;

import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;

public class Item {
	private String descripcion;
	private ValorMonetario valorItem;
	
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
