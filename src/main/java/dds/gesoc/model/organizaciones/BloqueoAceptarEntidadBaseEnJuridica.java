package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.BloquearEntidadBaseEnJuridicaException;
import dds.gesoc.exceptions.BloquearJuridicaNoAceptaEntBasesException;
import dds.gesoc.model.egresos.Egreso;

public class BloqueoAceptarEntidadBaseEnJuridica implements ComportamientoSegunReglaDeNegocio{
	private TipoRegla tipoDeRegla;

	public BloqueoAceptarEntidadBaseEnJuridica() {
	    this.tipoDeRegla =  TipoRegla.ENT_JURIDICA_AGREGA_ENT_BASES;
    }

	public TipoRegla getTipoDeRegla() {
		return this.tipoDeRegla;
	}

	//Bloquear la posibilidad de agregar entidades base a una entidad jurídica

	public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {
		if(entidad instanceof EntidadJuridica) {
			throw new BloquearJuridicaNoAceptaEntBasesException("Esta entidad jurídica no puede aceptar entidades bases");
		}
	}
}
