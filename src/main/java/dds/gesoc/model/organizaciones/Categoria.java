package dds.gesoc.model.organizaciones;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.model.egresos.Egreso;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Categoria extends EntidadPersistente{
	
	private String nombre;
	
	@Transient
	private List<ReglaDeNegocio> reglas = new ArrayList<>();
	
	public Categoria() {
	}
	public Categoria(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return nombre;
	}

	public void agregarReglaDeNegocio(ReglaDeNegocio regla) {
		if(!reglas.contains(regla))
			reglas.add(regla);
	}
	
	public void quitarReglaDeNegocio(ReglaDeNegocio regla) {
		if(reglas.contains(regla))
			reglas.remove(regla);
	}

	/*
	* Aplica las reglas de negocio relacionadas a la acciónn que quiera realizar una entidad
	* */
	public void aplicarReglasDeNegocio(Entidad entidad, ReglaDeNegocio regla, Double monto, Egreso egresoNuevo) {
		reglas.stream().filter(comportamiento -> comportamiento
				.equals(regla)).forEach(comportamiento -> comportamiento.ejecutarSobre(entidad, monto, egresoNuevo));
	}

}
