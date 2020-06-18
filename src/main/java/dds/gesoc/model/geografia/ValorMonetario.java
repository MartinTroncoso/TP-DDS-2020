package dds.gesoc.model.geografia;

public class ValorMonetario {
	
	private double cantidad;
	private Moneda moneda;
	
	public ValorMonetario(double cantidad, Moneda moneda) {
		this.cantidad = cantidad;
		this.moneda = moneda;
	}
	
	public double getCantidad() {
		return cantidad;
	}
	public Moneda getMoneda() {
		return moneda;
	}
}
