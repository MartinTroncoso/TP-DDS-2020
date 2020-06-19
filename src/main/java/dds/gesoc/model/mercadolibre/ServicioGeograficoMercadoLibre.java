package dds.gesoc.model.mercadolibre;

import java.util.List;
import java.util.stream.Collectors;

import dds.gesoc.model.geografia.Ciudad;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.Pais;
import dds.gesoc.model.geografia.Provincia;
import dds.gesoc.model.geografia.ServicioGeografico;

public class ServicioGeograficoMercadoLibre implements ServicioGeografico {

	MercadoLibreAPI api;
	
	public ServicioGeograficoMercadoLibre() {
		this.api = new MercadoLibreAPI();
	}
	
	public List<Pais> obtenerTodosLosPaises() {
		List<JPais> jpaises = api.obtenerJPaises();
		return jpaises.stream()
				.map(j -> new Pais(j.getId(), j.getName()))
				.collect(Collectors.toList());
	}
	
	public List<Provincia> obtenerProvinciasDeUnPais(Pais pais) {
		JPaisConProvincias jPais = api.obtenerJProvinciasDePais(pais.getId());
		return jPais.getStates()
				.stream()
				.map(s -> new Provincia(s.getId(), s.getName(), pais))
				.collect(Collectors.toList());
	}
	
	public List<Ciudad> obtenerCiudadesDeUnaProvincia(Provincia provincia) {
		JProvinciaConCiudades jProvincia = api.obtenerJCiudadesDeProvincia(provincia.getId());
		return jProvincia.getCities()
				.stream()
				.map(c -> new Ciudad(c.getId(), c.getName(), provincia))
				.collect(Collectors.toList());
	}

	public List<Moneda> obtenerTodasLasMonedas() {
		List<JMoneda> jmonedas = api.obtenerJMonedas();
		return jmonedas.stream()
				.map(j -> new Moneda(j.getId(), j.getDescription(), j.getSymbol()))
				.collect(Collectors.toList());
	}
}
