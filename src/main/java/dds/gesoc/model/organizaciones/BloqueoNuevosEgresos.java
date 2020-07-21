package dds.gesoc.model.organizaciones;import dds.gesoc.exceptions.BloquearEgresoException;
import dds.gesoc.model.egresos.Egreso;

public class BloqueoNuevosEgresos implements ComportamientoSegunReglaDeNegocio{
	Egreso egresoAgregar;
	public BloqueoNuevosEgresos(Egreso egresoAgregar) {
		this.egresoAgregar = egresoAgregar;
	}
	
	public void ejecutarSobre(Entidad entidad) {
		if(entidad.getMontosTotales() + egresoAgregar.valorTotal().getMonto() > entidad.getMontoEsperado()) {
			throw new BloquearEgresoException("Si se agrega el egreso, la entidad va a superar su monto esperado.");
		}
	}
}
