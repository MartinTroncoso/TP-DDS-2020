package dds.gesoc.model.RepoEntidades;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.model.organizaciones.Categoria;

public class RepoCategorias implements WithGlobalEntityManager{
	
	private static RepoCategorias repoCategorias;
	
	private RepoCategorias() {};

    public static RepoCategorias getInstance() {
        if (repoCategorias == null) {
        	repoCategorias = new RepoCategorias();
        }
        return repoCategorias;
    }

	public Categoria buscar(long id) {
		return entityManager().find(Categoria.class, id);
	}
    
}
