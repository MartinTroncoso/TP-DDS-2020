package dds.gesoc.model.geografia;

public class Moneda {

	private String id;
	private String descripcion;
	private String simbolo;
	
	public Moneda(String id, String descripcion, String simbolo) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.simbolo = simbolo;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
}
