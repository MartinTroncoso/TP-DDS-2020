package dds.gesoc.model.geografia;

public class ValorMonetario {
	
	private double monto;
	private Moneda moneda;
	
	public ValorMonetario(double monto, Moneda moneda) {
		this.monto = monto;
		this.moneda = moneda;
	}
	
	public double getMonto() {
		return monto;
	}
	public Moneda getMoneda() {
		return moneda;
	}
}
