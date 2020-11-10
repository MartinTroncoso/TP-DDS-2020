package dds.gesoc.model.geografia;

public class Ciudad {

	private String id;
	private String nombre;
	private Provincia provincia;

	public Ciudad(String id, String nombre, Provincia provincia) {
		this.id = id;
		this.nombre = nombre;
		this.provincia = provincia;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Provincia getProvincia() {
		return provincia;
	}
	
}
