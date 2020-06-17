package dds.gesoc.model.geografia;

import java.util.List;

public class Provincia {
	
	private String nombre;
	private List<Ciudad> ciudades;
	
	public Provincia(String nombre, List<Ciudad> ciudades) {
		this.nombre = nombre;
		this.ciudades = ciudades;
	}
	
	
}
