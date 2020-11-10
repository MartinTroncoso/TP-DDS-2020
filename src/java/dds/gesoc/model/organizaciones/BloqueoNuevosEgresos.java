package dds.gesoc.model.organizaciones;import dds.gesoc.exceptions.BloqueoEgresoExcedeMontoMaxException;
import dds.gesoc.model.egresos.Egreso;

public class BloqueoNuevosEgresos implements ComportamientoSegunReglaDeNegocio{
	private TipoRegla tipoDeRegla;

	public BloqueoNuevosEgresos() {
		this.tipoDeRegla = TipoRegla.ACEPTACION_NUEVOS_EGRESOS;
	}

	@Override
	public TipoRegla getTipoDeRegla() {
		return this.tipoDeRegla;
	}

	public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {

		//DUDA: El monto máximo es según la categoría o según cada egreso?
		if(entidad.getMontosTotales() + egresoNuevo.valorTotal().getMonto() > entidad.getMontoEsperado()) {
			throw new BloqueoEgresoExcedeMontoMaxException("Si se agrega el egreso, la entidad va a superar su monto esperado.");
		}
	}
}
