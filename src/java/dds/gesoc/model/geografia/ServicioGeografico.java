package dds.gesoc.model.geografia;

import java.util.List;

public interface ServicioGeografico {
	
	public List<Pais> obtenerTodosLosPaises();
	public List<Provincia> obtenerProvinciasDeUnPais(Pais pais);
	public List<Ciudad> obtenerCiudadesDeUnaProvincia(Provincia provincia);
	public List<Moneda> obtenerTodasLasMonedas();
}
