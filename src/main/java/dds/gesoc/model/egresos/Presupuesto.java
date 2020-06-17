package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;

public class Presupuesto {
	private Documento docComercial;
	private String detalle;
	private List<Item> items;
	private Proveedor unProveedor;

	
	public Presupuesto(Proveedor unProveedor, Documento docComercial, String detalle) {
		this.docComercial = docComercial;
		this.detalle = detalle;
		this.items = new ArrayList<>();
		this.unProveedor = unProveedor;
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

	public Proveedor getUnProveedor() {
		return unProveedor;
	}
}
