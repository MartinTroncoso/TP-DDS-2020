package dds.gesoc.model.geografia;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServicioGeograficoImpostor implements ServicioGeografico {

	public List<Pais> obtenerTodosLosPaises() {
		Pais argentina = new Pais("AR", "Argentina");
		return Stream.of(argentina).collect(Collectors.toList());
	}

	public List<Provincia> obtenerProvinciasDeUnPais(Pais pais) {
		Provincia caba = new Provincia("TUxBUENBUGw3M2E1", "Capital Federal", pais);
		Provincia otra = new Provincia("TUxBUENIQW8xMTNhOA", "Chaco", pais);
		return Stream.of(caba, otra).collect(Collectors.toList());
	}

	public List<Ciudad> obtenerCiudadesDeUnaProvincia(Provincia provincia) {
		//Todos tienen una capital federal (?
		Ciudad caba = new Ciudad("TUxBQ0NBUGZlZG1sYQ", "Capital Federal", provincia);
		return Stream.of(caba).collect(Collectors.toList());
	}

	public List<Moneda> obtenerTodasLasMonedas() {
		Moneda monedaMexicana = new Moneda("MXN", "Peso Mexicano", "$");
		Moneda monedaCubana = new Moneda("CUP", "Peso Cubano", "$");
		return Stream.of(monedaMexicana, monedaCubana).collect(Collectors.toList());
	}
}
