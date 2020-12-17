package dds.gesoc.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import db.EntityManagerHelper;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Item;

public class RepoEgresos implements WithGlobalEntityManager{

    private static RepoEgresos ourInstance;

    public static RepoEgresos getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoEgresos();
        }
        return ourInstance;
    }

    private RepoEgresos() {
    
    }
    
    public Egreso get(int index) {
    	return getEgresos().get(index);
    }
    
    public void agregarEgresoNuevo(Egreso unEgreso) {
    	entityManager().persist(unEgreso);
    }

    public void removerEgreso(Egreso unEgreso) {
    	getEgresos().remove(unEgreso);
    }

    public List<Egreso> egresosNoValidados() {
		return getEgresos().stream().filter(e -> !e.isValido()).collect(Collectors.toList());
    }
    
    public void validarEgresos() {
    	this.egresosNoValidados().forEach(egreso -> {
    		egreso.validar();
    		modificar(egreso);
    	});
    }
    
    public List<Egreso> getEgresos(){
		return entityManager().createQuery("from Egreso", Egreso.class).getResultList();
    }
    
    public Egreso buscar(int id){
        return entityManager().find(Egreso.class, id);
    }

    public void modificar(Egreso egreso) {
	    entityManager().merge(egreso);
	}

    public List<Item> getItemsDeEgreso(Egreso egreso){
		return entityManager().createQuery("from Item where egreso_id =" + egreso.getId(), Item.class).getResultList();
	}

    public Egreso buscarEgresoDeItem(Item item) {
    	return entityManager().createQuery("select egreso_id from Item where id=" + item.getId(), Egreso.class).getSingleResult();
	}
}
