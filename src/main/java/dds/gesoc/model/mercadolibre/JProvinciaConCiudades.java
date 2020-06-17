package dds.gesoc.model.mercadolibre;

import java.util.List;

public class JProvinciaConCiudades {
	
	private String id;
	private String name;
	private List<JCiudad> cities;
	
	public JProvinciaConCiudades(String id, String name, List<JCiudad> cities) {
		this.id = id;
		this.name = name;
		this.cities = cities;
	}
	
	public List<JCiudad> getCities() {
		return cities;
	}
}
