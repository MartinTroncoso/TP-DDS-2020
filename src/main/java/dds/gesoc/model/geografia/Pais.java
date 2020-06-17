package dds.gesoc.model.geografia;

import java.util.List;

public class Pais {

	private String nombre;
	private List<Provincia> provincias;
	
	public Pais(String nombre, List<Provincia> provincias) {
		this.nombre = nombre;
		this.provincias = provincias;
	}
	
}
