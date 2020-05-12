package dds.gesoc.model.egresos;

import java.util.regex.Pattern;

import dds.gesoc.model.egresos.exceptions.TarjetaInvalidaException;

public class MedioPago {
	private TipoMedioPago tipo;
	private String numero;
	private String nroTrajetaValidaRegex = "\\d{16}";
	
	public MedioPago(TipoMedioPago tipo) {
		this.tipo = tipo;
	}
	
	public MedioPago(TipoMedioPago tipo, String numero) {
		this.validarNumero(tipo, numero);
	}
	
	public boolean esTarjeta(TipoMedioPago tipo) {
		return tipo == TipoMedioPago.TARJETA_DE_CREDITO || tipo == TipoMedioPago.TARJETA_DE_DEBITO;
	}
	
	public void validarNumero(TipoMedioPago tipo, String numero) {
		if(this.esTarjeta(tipo) && !Pattern.matches(nroTrajetaValidaRegex, numero)) {
			this.setTipo(tipo);
			this.setNumero(numero);
		}else {
			throw new TarjetaInvalidaException("El metodo de pago ingresado no es tarjeta o el numero de tarjeta es incorrecto");
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
