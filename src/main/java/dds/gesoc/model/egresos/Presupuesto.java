package dds.gesoc.model.egresos;

import dds.gesoc.exceptions.PresupuestoSinEgresoAsociadoException;

import java.util.ArrayList;
import java.util.List;

public class Presupuesto {
	private Documento docComercial;
	private String detalle;
	private List<Item> items;
	private Proveedor unProveedor;
	private Egreso egresoAsociado;

	
	public Presupuesto(Proveedor unProveedor, Documento docComercial, String detalle, Egreso unEgreso) {
		this.docComercial = docComercial;
		this.detalle = detalle;
		this.items = new ArrayList<>();
		this.unProveedor = unProveedor;

		if (unEgreso == null)
			throw new PresupuestoSinEgresoAsociadoException("No se pueden crear presupuestos sin un egreso asociado");
		this.egresoAsociado = unEgreso;
		unEgreso.agregarPresupuesto(this);
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
