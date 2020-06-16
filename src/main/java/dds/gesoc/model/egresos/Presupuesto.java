package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;

public class Presupuesto {
	Documento docComercial;
	String detalle;
	List<Item> items;
	
	public Presupuesto(Documento docComercial, String detalle) {
		this.docComercial = docComercial;
		this.detalle = detalle;
		this.items = new ArrayList<>();
	}

	public Documento getDocComercial() {
		return this.docComercial;
	}

	public void setDocComercial(Documento docComercial) {
		this.docComercial = docComercial;
	}

	public List<Item> getItems() {
		return items;
	}

	public void agregarItem(Item item) {
		this.getItems().add(item);
	}
	
	public double valorTotal() {
		return this.getItems().stream().mapToDouble(Item::getValor).sum();
	}
}
