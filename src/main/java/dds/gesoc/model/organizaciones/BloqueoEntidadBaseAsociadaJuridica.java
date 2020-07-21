package dds.gesoc.model.organizaciones;

public class BloqueoEntidadBaseAsociadaJuridica implements ComportamientoSegunReglaDeNegocio{
	public void ejecutarSobre(Entidad entidad) {
		if(entidad instanceof EntidadJuridica) {
			((EntidadJuridica) entidad).getEntidades().stream().filter(unaEntidad -> !(unaEntidad instanceof EntidadBase));
			//acá iría una excepción?
		}
	}
}
