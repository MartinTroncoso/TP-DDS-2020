package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	private String nombre; 
	private List<ComportamientoSegunReglaDeNegocio> comportamientoSegunReglasDeNegocio = new ArrayList<>();
	
	public Categoria(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return nombre;
	}

	public void agregarReglaDeNegocio(ComportamientoSegunReglaDeNegocio comportamiento) {
		if(!comportamientoSegunReglasDeNegocio.contains(comportamiento))
			comportamientoSegunReglasDeNegocio.add(comportamiento);
	}
	
	public void quitarReglaDeNegocio(ComportamientoSegunReglaDeNegocio comportamiento) {
		if(comportamientoSegunReglasDeNegocio.contains(comportamiento))
			comportamientoSegunReglasDeNegocio.remove(comportamiento);
	}
	
	public void aplicarReglasDeNegocio(Entidad entidad) {
		comportamientoSegunReglasDeNegocio.forEach(comportamiento -> comportamiento.ejecutarSobre(entidad));
	}

}
