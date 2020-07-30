package dds.gesoc.model.organizaciones;

public class BloqueoEntidadBaseAsociadaJuridica implements ComportamientoSegunReglaDeNegocio{
	TipoRegla tipoDeRegla;
	public void ejecutarSobre(Entidad entidad) {
		if(entidad instanceof EntidadJuridica) {
			((EntidadJuridica) entidad).getEntidades().stream().filter(unaEntidad -> !(unaEntidad instanceof EntidadBase));
			//ac� ir�a una excepci�n?
		}
	}
}
