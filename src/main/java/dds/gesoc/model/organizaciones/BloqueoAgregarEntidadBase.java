package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.BloquearEntidadBaseEnJuridicaException;

public class BloqueoAgregarEntidadBase implements ComportamientoSegunReglaDeNegocio{
	Entidad entidadAgregar;
	public BloqueoAgregarEntidadBase(Entidad entidadAgregar) {
		this.entidadAgregar = entidadAgregar;
	}
	
	public void ejecutarSobre(Entidad entidad) {
		if((entidadAgregar instanceof EntidadBase) && (entidad instanceof EntidadJuridica)) {
			throw new BloquearEntidadBaseEnJuridicaException("No se pueden agregar Entidades Base en una Jurídica.");
		}
	}
}
