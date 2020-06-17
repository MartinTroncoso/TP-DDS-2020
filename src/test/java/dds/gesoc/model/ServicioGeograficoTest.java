package dds.gesoc.model;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.gesoc.model.geografia.Ciudad;
import dds.gesoc.model.geografia.Pais;
import dds.gesoc.model.geografia.Provincia;
import dds.gesoc.model.geografia.ServicioGeografico;
import dds.gesoc.model.mercadolibre.ServicioGeograficoMercadoLibre;


public class ServicioGeograficoTest {

	private ServicioGeografico servicio;

	@Before
	public void init() {

		servicio = new ServicioGeograficoMercadoLibre();
	}

	@Test
	public void obtengoUnaListaDePaises() {
		List<Pais> paises = servicio.obtenerTodosLosPaises();
		Pais unPais = this.getRandomItem(paises);
		System.out.println(unPais.getId()+","+unPais.getNombre());
		Assert.assertTrue(paises.size() > 0);
		Assert.assertNotNull(unPais);
	}
	
	@Test
	public void obtengoUnaListaDeProvinciasDeUnPaisCualquiera() {
		Pais unPaisAleatorio = this.getRandomItem(servicio.obtenerTodosLosPaises());
		List<Provincia> provincias = servicio.obtenerProvinciasDeUnPais(unPaisAleatorio);
		Provincia unaProvincia = this.getRandomItem(provincias);
		System.out.println(unPaisAleatorio.getId()+","+unPaisAleatorio.getNombre());
		System.out.println(unaProvincia.getId()+","+unaProvincia.getNombre());
		Assert.assertNotNull(provincias);
	}
	
	@Test
	public void obtengoUnaCiudadCualquiera() {
		Pais unPais = this.getRandomItem(servicio.obtenerTodosLosPaises());
		Provincia unaProvincia = this.getRandomItem(servicio.obtenerProvinciasDeUnPais(unPais));
		Ciudad ciudadAleatoria = this.getRandomItem(servicio.obtenerCiudadesDeUnaProvincia(unaProvincia));
		System.out.println("Info de la ciudad:");
		System.out.println("ID: " + ciudadAleatoria.getId());
		System.out.println("Nombre: " + ciudadAleatoria.getNombre());
		System.out.println("Provincia: " + ciudadAleatoria.getProvincia().getNombre());
		System.out.println("Pais: " + ciudadAleatoria.getProvincia().getPais().getNombre());
		Assert.assertNotNull(ciudadAleatoria);
	}
	
	private <T> T getRandomItem(List<T> list) {
		if(list.size() == 0)
			throw new ArrayIndexOutOfBoundsException();
		Random random = new Random();
		int randomIndex = random.nextInt(list.size());
		return list.get(randomIndex);
	}
}
