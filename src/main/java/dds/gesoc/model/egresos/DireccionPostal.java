package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;
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
		for(Pais unPais:servicio.obtenerTodosLosPaises()) {
			if(pais.equalsIgnoreCase(unPais.getNombre())) {
				this.pais = pais;
				return;
			}
		}
		
		throw new DireccionPostalIncorrectaException("No se admite un proveedor de este país");
	}
	
	public void verificarProvincia(ServicioGeograficoMercadoLibre servicio, String provincia) {
		Pais paisProveedor = servicio.obtenerTodosLosPaises()
				.stream().filter(p -> p.getNombre() == this.getPais())
				.collect(Collectors.toList())
				.get(0);
		
		for(Provincia unaProvincia:servicio.obtenerProvinciasDeUnPais(paisProveedor)) {
			if(provincia.equalsIgnoreCase(unaProvincia.getNombre())) {
				this.provincia = provincia;
				return;
			}
		}
		
		throw new DireccionPostalIncorrectaException("La provincia del proveedor " + provincia + " no pertenece al país " + this.getPais());
	}
	
	public void verificarCiudad(ServicioGeograficoMercadoLibre servicio, String ciudad) {
		Pais paisProveedor = servicio.obtenerTodosLosPaises()
				.stream().filter(p -> p.getNombre() == this.getPais())
				.collect(Collectors.toList())
				.get(0);
		
		Provincia provinciaProveedor = servicio.obtenerProvinciasDeUnPais(paisProveedor)
				.stream().filter(p -> p.getNombre() == this.getProvincia())
				.collect(Collectors.toList())
				.get(0);
		
		for(Ciudad unaCiudad:servicio.obtenerCiudadesDeUnaProvincia(provinciaProveedor)) {
			if(ciudad.equalsIgnoreCase(unaCiudad.getNombre())) {
				this.ciudad = ciudad;
				return;
			}
		}
		
		throw new DireccionPostalIncorrectaException("La ciudad del proveedor " + ciudad + " no pertenece a la provincia " + this.getProvincia());
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
