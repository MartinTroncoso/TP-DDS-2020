package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;

public class Organizacion  {

    private List<Entidad> estructurasDeEntidades = new ArrayList<>();
    private List<Categoria> categoriasDeEntidades = new ArrayList<>();

    public void agregarEntidad(Entidad unaEntidad) {
        estructurasDeEntidades.add(unaEntidad);
    }
    
    public void agregarCategoria(Categoria unaCategoria) {
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
    }
    
    public void aplicarReglaDeNegocioA(Categoria unaCategoria, Entidad unaEntidad) {
    	unaCategoria.aplicarReglasDeNegocio(unaEntidad);
    }
}
