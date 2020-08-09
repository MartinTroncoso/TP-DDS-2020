package dds.gesoc.model.organizaciones;

import dds.gesoc.model.egresos.Egreso;

public interface ComportamientoSegunReglaDeNegocio {

	void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo);
	TipoRegla getTipoDeRegla();
}

