package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.model.organizaciones.Entidad;

public class RepoProveedores implements WithGlobalEntityManager{
    private static RepoProveedores ourInstance;

    public static RepoProveedores getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoProveedores();
        }
        return ourInstance;
    }

    private RepoProveedores() {}
    
    public void agregarProveedor(Proveedor proveedor) {
    	entityManager().persist(proveedor);
    }
    
    public List<Proveedor> getProveedores(){
    	return entityManager().createQuery("from proveedor", Proveedor.class).getResultList();
    }
    
    public Proveedor buscar(long id){
        return entityManager().find(Proveedor.class, id);
    }
}
