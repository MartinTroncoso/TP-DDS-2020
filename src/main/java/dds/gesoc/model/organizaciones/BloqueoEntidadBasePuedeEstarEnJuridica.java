package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.BloquearEntidadBaseEnJuridicaException;
import dds.gesoc.model.egresos.Egreso;

public class BloqueoEntidadBasePuedeEstarEnJuridica implements ComportamientoSegunReglaDeNegocio{

	TipoRegla tipoDeRegla;

	public BloqueoEntidadBasePuedeEstarEnJuridica(TipoRegla tipoDeRegla) {
		this.tipoDeRegla = TipoRegla.ENT_BASE_FORMA_PARTE_ENT_JURIDICA;
	}

	@Override
	public TipoRegla getTipoDeRegla() {
		return tipoDeRegla;
	}

	public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {
		if(entidad instanceof EntidadBase) {
			throw new BloquearEntidadBaseEnJuridicaException("No se puede agregar ésta entidad base en una jurídica");
		}
	}
}
