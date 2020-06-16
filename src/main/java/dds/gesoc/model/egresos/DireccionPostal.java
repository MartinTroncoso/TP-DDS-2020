package dds.gesoc.model.egresos;

public class DireccionPostal {
	private String direccion;
	private String ciudad;
	private String provincia;
	private String pais;
	
	public DireccionPostal(String direccion, String ciudad, String provincia, String pais) {
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.pais = pais;
		
		//TODO: con la api se deberia verificar si el pais del Proveedor es uno de los 
		//		paises que nos da la api, lo mismo con las provincias y ciudades
	}

	public String getDireccion() {
		return direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getPais() {
		return pais;
	}
}
