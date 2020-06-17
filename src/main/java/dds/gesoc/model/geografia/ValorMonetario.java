package dds.gesoc.model.geografia;

public class ValorMonetario {
	
	private int cantidad;
	private Moneda moneda;
	
	public ValorMonetario(int cantidad, Moneda moneda) {
		this.cantidad = cantidad;
		this.moneda = moneda;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public Moneda getMoneda() {
		return moneda;
	}
}
