package dds.gesoc.model.egresos;

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.exceptions.ValidarTarjetaException;

@Entity
public class MedioPago extends EntidadPersistente{
	
	@Enumerated(value = EnumType.STRING)
	private TipoMedioPago tipo;
	private String numero;
	@Transient
	private String nroTrajetaValidaRegex = "\\d{16}";
	
	public MedioPago() {
	}
	
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
		if(this.esTarjeta() && Pattern.matches(nroTrajetaValidaRegex, numero)) {
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
