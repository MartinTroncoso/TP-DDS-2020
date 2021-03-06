package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dds.gesoc.entities.EntidadPersistente;

@Entity
public class Organizacion  extends EntidadPersistente{
	
	String nombre;
	@OneToMany
	@JoinColumn(name = "organizacion_id")
    private List<Entidad> estructurasDeEntidades = new ArrayList<>();
    //private List<Categoria> categoriasDeEntidades = new ArrayList<>();

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarEntidad(Entidad unaEntidad) {
        estructurasDeEntidades.add(unaEntidad);
    }


/*    public void agregarCategoria(Categoria unaCategoria) {
    	if(!categoriasDeEntidades.contains(unaCategoria))
    		categoriasDeEntidades.add(unaCategoria);
    }

    public void removerCategoria(Categoria unaCategoria) {
    	if(categoriasDeEntidades.contains(unaCategoria))
    		categoriasDeEntidades.remove(unaCategoria);
    }

    public void agregarReglaDeNegocioA(Categoria unaCategoria, ComportamientoSegunReglaDeNegocio comportamiento) {
    	unaCategoria.agregarReglaDeNegocio(comportamiento);
    }

    public void removerReglaDeNegocioA(Categoria unaCategoria, ComportamientoSegunReglaDeNegocio comportamiento) {
    	unaCategoria.agregarReglaDeNegocio(comportamiento);
    }*/

}
