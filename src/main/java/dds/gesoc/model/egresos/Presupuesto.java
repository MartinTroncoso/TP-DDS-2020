package dds.gesoc.model.egresos;

import dds.gesoc.exceptions.PresupuestoSinEgresoAsociadoException;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;

import java.util.ArrayList;
import java.util.List;

public class Presupuesto {
	private Documento docComercial;
	private String detalle;
	private List<Item> items;
	private Proveedor unProveedor;
	private Egreso egresoAsociado;
	private Moneda moneda;

	
	public Presupuesto(Proveedor unProveedor, Documento docComercial, Moneda moneda, String detalle, Egreso unEgreso) {
		this.docComercial = docComercial;
		this.setDetalle(detalle);
		this.items = new ArrayList<>();
		this.unProveedor = unProveedor;
		this.moneda = moneda;
		this.establecerEgresoAsociado(unEgreso);
	}

	private void establecerEgresoAsociado(Egreso unEgreso) {
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
	
	public ValorMonetario valorTotal() {
		return new ValorMonetario(this.getItems().stream().mapToDouble(Item::getMonto).sum(), moneda);
	}

	public double valorTotalNumerico() {
		return valorTotal().getMonto();
	}

	public Proveedor getUnProveedor() {
		return unProveedor;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public boolean compraRealizadaSegunEstePresupuesto(Egreso unEgreso) {
		return this.getUnProveedor().equals(unEgreso.getProveedor())
			   && this.getItems().equals(unEgreso.getItems())
			   && this.valorTotal().getMonto() == unEgreso.valorTotal().getMonto();
	}

}
