package dds.gesoc.model.egresos;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import db.EntityManagerHelper;

public class RepoItems implements WithGlobalEntityManager{
	
	private static RepoItems ourInstance;
	
	public static RepoItems getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoItems();
        }
        return ourInstance;
    }
	
	public void agregarItemNuevo(Item unItem) {
    	entityManager().persist(unItem);
    }

	public Item buscar(int id) {
		return entityManager().find(Item.class, id);
	}
	
	public void modificar(Item item) {
	    EntityManagerHelper.getEntityManager().getTransaction().begin();
	    EntityManagerHelper.getEntityManager().merge(item);
	    EntityManagerHelper.getEntityManager().getTransaction().commit();
	}
	
	public List<Item> getItemsDeEgreso(Egreso egreso){
		return entityManager().createQuery("from Item where egreso_id =" + egreso.getId(), Item.class).getResultList();
	}

	public Item buscarItemDeEgreso(Egreso egreso, String id) {
		return entityManager()
				.createQuery("from Item where id = " + id + "and egreso_id = " + egreso.getId(), Item.class)
				.getSingleResult();
	}
}
