package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.BloqueoEgresoExcedeMontoMaxException;
import dds.gesoc.exceptions.BloqueoEntidadBaseNoPuedePertenecerAJuridicasException;
import dds.gesoc.exceptions.BloqueoJuridicaNoAceptaEntBasesException;
import dds.gesoc.model.egresos.Egreso;

public enum ReglaDeNegocio {
	ACEPTACION_NUEVOS_EGRESOS{
		@Override
		public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {

			//DUDA: El monto máximo es según la categoría o según cada egreso?
			if(entidad.getMontosTotales() + egresoNuevo.valorTotal().getMonto() > entidad.getMontoEsperado()) {
				throw new BloqueoEgresoExcedeMontoMaxException("Si se agrega el egreso, la entidad va a superar su monto esperado.");
			}
		}
		
	},
	ENT_JURIDICA_AGREGA_ENT_BASES{
		@Override
		public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {
			if(entidad instanceof EntidadJuridica) {
				throw new BloqueoJuridicaNoAceptaEntBasesException("Esta entidad jurídica no puede aceptar entidades bases");
			}
		}
		
	},
	ENT_BASE_FORMA_PARTE_ENT_JURIDICA{
		@Override
		public void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo) {
			if(entidad instanceof EntidadBase) {
				throw new BloqueoEntidadBaseNoPuedePertenecerAJuridicasException("No se puede agregar ésta entidad base en una jurídica");
			}
		}
	};
	public abstract void ejecutarSobre(Entidad entidad, Double monto, Egreso egresoNuevo);
}
