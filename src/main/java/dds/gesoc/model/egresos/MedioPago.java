package dds.gesoc.model.egresos;

import java.util.regex.Pattern;

import dds.gesoc.model.egresos.exceptions.ValidarTarjetaException;

public class MedioPago {
	private TipoMedioPago tipo;
	private String numero;
	private String nroTrajetaValidaRegex = "\\d{16}";
	
	public MedioPago(TipoMedioPago tipo) {
		this.tipo = tipo;
	}
	
	public MedioPago(TipoMedioPago tipo, String numero) {
		this.tipo = tipo;
		this.establecerNroTarjeta(numero);
	}
	
	public boolean esTarjeta() {
		return this.getTipo() == TipoMedioPago.TARJETA_DE_CREDITO || this.getTipo() == TipoMedioPago.TARJETA_DE_DEBITO;
	}
	
	public void establecerNroTarjeta(String numero) {
		if(this.esTarjeta() && !Pattern.matches(nroTrajetaValidaRegex, numero)) {
			this.setNumero(numero);
		}else {
			throw new ValidarTarjetaException("El medio de pago ingresado no es tarjeta o el numero de tarjeta es incorrecto");
		}
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getNumero() {
		return this.numero;
	}

	public void setTipo(TipoMedioPago tipo) {
		this.tipo = tipo;		
	}

	public TipoMedioPago getTipo() {
		return this.tipo;
	}
}
