package dds.gesoc.model.organizaciones;

import dds.gesoc.model.egresos.Egreso;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	private String nombre;
	private List<ComportamientoSegunReglaDeNegocio> comportamientosSegunReglasDeNegocio = new ArrayList<>();
	
	public Categoria(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return nombre;
	}

	public void agregarReglaDeNegocio(ComportamientoSegunReglaDeNegocio comportamiento) {
		if(!comportamientosSegunReglasDeNegocio.contains(comportamiento))
			comportamientosSegunReglasDeNegocio.add(comportamiento);
	}
	
	public void quitarReglaDeNegocio(ComportamientoSegunReglaDeNegocio comportamiento) {
		if(comportamientosSegunReglasDeNegocio.contains(comportamiento))
			comportamientosSegunReglasDeNegocio.remove(comportamiento);
	}


	/*
	* Aplica las reglas de negocio relacionadas a la acciónn que quiera realizar una entidad
	* */
	public void aplicarReglasDeNegocio(Entidad entidad, TipoRegla tipoRegla, Double monto, Egreso egresoNuevo) {
		comportamientosSegunReglasDeNegocio.stream().filter(comportamiento -> comportamiento.getTipoDeRegla()
				.equals(tipoRegla)).forEach(comportamiento -> comportamiento.ejecutarSobre(entidad, monto, egresoNuevo));
	}

}
