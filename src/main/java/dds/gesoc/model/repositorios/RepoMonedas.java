package dds.gesoc.model.repositorios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.model.geografia.Moneda;

public class RepoMonedas implements WithGlobalEntityManager{
	private static RepoMonedas ourInstance;

    public static RepoMonedas getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoMonedas();
        }
        return ourInstance;
    }

    private RepoMonedas() {}
    
    public void agregarMoneda(Moneda moneda) {
    	entityManager().persist(moneda);
    }
    
    public List<Moneda> getMonedas(){
    	return entityManager().createQuery("from Moneda", Moneda.class).getResultList();
    }
    
    public Moneda buscar(int id){
        return entityManager().find(Moneda.class, id);
    }
}