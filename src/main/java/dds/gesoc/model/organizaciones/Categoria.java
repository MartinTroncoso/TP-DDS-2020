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
		comportamientoSegunReglasDeNegocio.add(comportamiento);
	}
	
	public void quitarReglaDeNegocio(int posicion) {
		comportamientoSegunReglasDeNegocio.remove(posicion);
	}
	
	public void aplicarReglasDeNegocio(Entidad entidad) {
		comportamientoSegunReglasDeNegocio.forEach(comportamiento -> comportamiento.ejecutarSobre(entidad));
	}

}
