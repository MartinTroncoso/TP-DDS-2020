package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.BloqueoEntidadBaseNoPuedePertenecerAJuridicasException;
import dds.gesoc.model.egresos.Egreso;

public class BloqueoEntidadBaseNoPuedePertenecerAJuridicas implements ComportamientoSegunReglaDeNegocio{

	TipoRegla tipoDeRegla;

	public BloqueoEntidadBaseNoPuedePertenecerAJuridicas() {
		this.tipoDeRegla = TipoRegla.ENT_BASE_FORMA_PARTE_ENT_JURIDICA;
	}

	@Override
	public TipoRegla getTipoDeRegla() {
		return tipoDeRegla;
	}

	public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {
		if(entidad instanceof EntidadBase) {
			throw new BloqueoEntidadBaseNoPuedePertenecerAJuridicasException("No se puede agregar ésta entidad base en una jurídica");
		}
	}
}
