package dds.gesoc.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import db.EntityManagerHelper;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Presupuesto;

public class RepoPresupuestos implements WithGlobalEntityManager{
    private static RepoPresupuestos ourInstance;
    private List<Presupuesto> presupuestos = new ArrayList<>();

    public static RepoPresupuestos getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoPresupuestos();
        }
        return ourInstance;
    }

    private RepoPresupuestos() {}
    
    public Presupuesto get(int index) {
    	return presupuestos.get(index);
    }

    public void agregarPresupuesto(Presupuesto presupuesto) {
    	entityManager().persist(presupuesto);
    }

    public void removerPresupuesto(Presupuesto presupuesto) {
    	presupuestos.remove(presupuesto);
    }
    
    public List<Presupuesto> getPresupuestos(){
		return entityManager().createQuery("from Presupuesto", Presupuesto.class).getResultList();
    }
    
    public Presupuesto buscar(int id){
        return entityManager().find(Presupuesto.class, id);
    }
    
    public void modificar(Presupuesto presupuesto) {
	    EntityManagerHelper.getEntityManager().getTransaction().begin();
	    EntityManagerHelper.getEntityManager().merge(presupuesto);
	    EntityManagerHelper.getEntityManager().getTransaction().commit();
	}
    
	public List<Presupuesto> getPresupuestosDeEgreso(Egreso egreso){
		return entityManager().createQuery("from Presupuesto where egreso_id =" + egreso.getId(), Presupuesto.class).getResultList();
	}
}
