package dds.gesoc.model.geografia;

import java.util.List;

public class Provincia {
	
	private String id;
	private String nombre;
	private Pais pais;
	
	public Provincia(String id, String nombre, Pais pais) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Pais getPais() {
		return pais;
	}
	
}
