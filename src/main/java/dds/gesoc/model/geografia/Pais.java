package dds.gesoc.model.geografia;


public class Pais {

	private String id;
	private String nombre;


	public Pais(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
}
