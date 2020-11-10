package dds.gesoc.model.geografia;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;


@Embeddable
public class ValorMonetario {
	
	private double monto;
	
	@ManyToOne
	private Moneda moneda;
	
	public ValorMonetario() {
	}
	
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

	public double getMontoConvertido(Moneda moneda) {

		//HACER UNA CONVERSIÓN DE MONEDAS...
		return monto;
	}
}
