package dds.gesoc.model.mercadolibre;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class MercadoLibreAPI {

	public static final String API_MERCADO_LIBRE = "https://api.mercadolibre.com";
	public static final String RESOURCE_COUNTRIES = "classified_locations/countries";
	public static final String RESOURCE_STATES = "classified_locations/states";
	public static final String RESOURCE_CURRENCIES = "currencies";
	
	public MercadoLibreAPI() {
		
	}
	
	public List<JPais> obtenerJPaises() {
		String respuesta = this.getResponseJson(RESOURCE_COUNTRIES);
		Type listTypePaises = new TypeToken<ArrayList<JPais>>(){}.getType();
		return new Gson().fromJson(respuesta, listTypePaises);
	}
	
	public JPaisConProvincias obtenerJProvinciasDePais(String idPais){
		String respuesta = this.getResponseJson(RESOURCE_COUNTRIES + "/" + idPais);
		return new Gson().fromJson(respuesta, JPaisConProvincias.class);
	}
	
	public JProvinciaConCiudades obtenerJCiudadesDeProvincia(String idProvincia) {
		String respuesta = this.getResponseJson(RESOURCE_STATES + "/" + idProvincia);
		return new Gson().fromJson(respuesta, JProvinciaConCiudades.class);
	}
	
	public List<JMoneda> obtenerJMonedas() {
		String respuesta = this.getResponseJson(RESOURCE_CURRENCIES);
		Type listTypeMonedas = new TypeToken<ArrayList<JMoneda>>(){}.getType();
		return new Gson().fromJson(respuesta, listTypeMonedas);
	}
	
	private String getResponseJson(String path) {
		return Client.create().resource(API_MERCADO_LIBRE).path(path).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class).getEntity(String.class);
	}
}
