package dds.gesoc.model.repositorios;

import java.util.List;

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

    public Categoria buscar(int id) {
		return entityManager().find(Categoria.class, id);
	}
	
	public List<Categoria> listar() {
		return entityManager().createQuery("from Categoria", Categoria.class)
		.getResultList();
	}
    
}
