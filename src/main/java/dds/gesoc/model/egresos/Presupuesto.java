package dds.gesoc.model.egresos;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.exceptions.PresupuestoSinEgresoAsociadoException;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "presupuesto")
public class Presupuesto extends EntidadPersistente{
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "documento_id")
	private Documento docComercial;
	
	@Column
	private String detalle;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "presupuesto_id")
	private List<Item> items;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "proveedor_id", referencedColumnName = "id")
	private Proveedor proveedor;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "egreso_id", referencedColumnName = "id")
	private Egreso egresoAsociado;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "moneda_id", referencedColumnName = "id")
	private Moneda moneda;

	public Presupuesto() {
	}
	
	public Presupuesto(Proveedor unProveedor, Documento docComercial, Moneda moneda, String detalle, Egreso unEgreso) {
		this.docComercial = docComercial;
		this.setDetalle(detalle);
		this.items = new ArrayList<>();
		this.proveedor = unProveedor;
		this.moneda = moneda;
		this.establecerEgresoAsociado(unEgreso);
	}

	public void establecerEgresoAsociado(Egreso unEgreso) {
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

	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public boolean compraRealizadaSegunEstePresupuesto(Egreso unEgreso) {
		return this.getProveedor().equals(unEgreso.getProveedor())
			   && this.getItems().equals(unEgreso.getItems())
			   && this.valorTotal().getMonto() == unEgreso.valorTotal().getMonto();
	}

	public Moneda getMoneda() {
		return moneda;
	}
	
	public Egreso getEgresoAsociado() {
		return egresoAsociado;
	}
}
