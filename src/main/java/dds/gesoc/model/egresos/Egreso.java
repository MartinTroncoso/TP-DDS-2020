package dds.gesoc.model.egresos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Egreso {
	private Proveedor proveedor;
	private LocalDate fechaOperacion;
	private Documento docComercial;
	private MedioPago medioPago;
	private List<Item> items;
	
	public Egreso(Proveedor prov, MedioPago medioPago) {
		this.proveedor = prov;
		this.medioPago = medioPago;
		this.fechaOperacion = LocalDate.now();
		this.items = new ArrayList<Item>();
	}
	
	public Egreso(Proveedor prov, Documento documento, MedioPago medioPago) {
		this.proveedor = prov;
		this.docComercial = documento;
		this.medioPago = medioPago;
		this.fechaOperacion = LocalDate.now();
		this.items = new ArrayList<Item>();
	}
	
	public double valorTotal() {
		return this.getItems().stream().mapToDouble(Item::getValor).sum();
	}
	
	public void aniadirItem(Item item) {
		this.items.add(item);
	}
	
	public List<Item> getItems(){
		return this.items;
	}
}
