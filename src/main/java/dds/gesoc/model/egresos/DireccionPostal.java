package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dds.gesoc.exceptions.DireccionPostalIncorrectaException;
import dds.gesoc.model.geografia.Ciudad;
import dds.gesoc.model.geografia.Pais;
import dds.gesoc.model.geografia.Provincia;
import dds.gesoc.model.mercadolibre.ServicioGeograficoMercadoLibre;

public class DireccionPostal {
	private String pais;
	private String provincia;
	private String ciudad;
	private String direccion;
	
	public DireccionPostal(String pais, String provincia, String ciudad, String direccion) {
		ServicioGeograficoMercadoLibre servicio = new ServicioGeograficoMercadoLibre();
		this.verificarPais(servicio, pais);
		this.verificarProvincia(servicio, provincia);
		this.verificarCiudad(servicio, ciudad);
		this.direccion = direccion;
	}
	
	public void verificarPais(ServicioGeograficoMercadoLibre servicio, String pais) {
		Optional<Pais> posiblePaisProveedor = servicio.obtenerTodosLosPaises()
				.stream().filter(p -> pais.equalsIgnoreCase(p.getNombre()))
				.findFirst();
		
		if(!posiblePaisProveedor.isPresent()) {
			throw new DireccionPostalIncorrectaException("No se admite un proveedor de este país");
		}
		
		this.pais = pais;
	}
	
	public void verificarProvincia(ServicioGeograficoMercadoLibre servicio, String provincia) {
		Pais paisProveedor = pasarStringAPais(servicio);
		Optional<Provincia> posibleProvinciaProveedor = servicio.obtenerProvinciasDeUnPais(paisProveedor)
				.stream().filter(p -> provincia.equalsIgnoreCase(p.getNombre()))
				.findFirst();
		
		if(!posibleProvinciaProveedor.isPresent()) {
			throw new DireccionPostalIncorrectaException("La provincia " + provincia + " no pertenece al país " + this.getPais());
		}
		
		this.provincia = provincia;
	}
	
	public void verificarCiudad(ServicioGeograficoMercadoLibre servicio, String ciudad) {
		Provincia provinciaProveedor = pasarStringAProvincia(servicio, pasarStringAPais(servicio));
		Optional<Ciudad> posibleCiudadProveedor = servicio.obtenerCiudadesDeUnaProvincia(provinciaProveedor)
				.stream().filter(p -> ciudad.equalsIgnoreCase(p.getNombre()))
				.findFirst();
		
		if(!posibleCiudadProveedor.isPresent()) {
			throw new DireccionPostalIncorrectaException("La ciudad " + ciudad + " no pertenece a la provincia " + this.getProvincia());
		}

		this.ciudad = ciudad;
	}
	
	/*
	 * "convierte" el pais del Proveedor del tipo String al tipo Pais 
	 * (digo "convierte" porque consigue el Pais de la API de Mercadolibre que sea igual al pais del Proveedor)
	 * 
	 * Lo mismo con la provincia del Proveedor
	 * 
	 * Veo repetición de lógica pero por el momento esto es lo que puedo hacer
	 */
	
	private Pais pasarStringAPais(ServicioGeograficoMercadoLibre servicio) {
		return servicio.obtenerTodosLosPaises()
				.stream().filter(p -> this.getPais().equalsIgnoreCase(p.getNombre()))
				.collect(Collectors.toList())
				.get(0);
	}
	
	private Provincia pasarStringAProvincia(ServicioGeograficoMercadoLibre servicio, Pais paisProveedor) {
		return servicio.obtenerProvinciasDeUnPais(paisProveedor)
				.stream().filter(p -> this.getProvincia().equalsIgnoreCase(p.getNombre()))
				.collect(Collectors.toList())
				.get(0);
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
