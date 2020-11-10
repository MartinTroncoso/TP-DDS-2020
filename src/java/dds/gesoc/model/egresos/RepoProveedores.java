package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepoProveedores implements WithGlobalEntityManager{
    private static RepoProveedores ourInstance;
    private List<Proveedor> proveedores = new ArrayList<>();

    public static RepoProveedores getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoProveedores();
        }
        return ourInstance;
    }

    private RepoProveedores() {}
    
    public void agregarProveedor(Proveedor proveedor) {
    	proveedores.add(proveedor);
    }
    
    public List<Proveedor> getProveedores(){
    	return proveedores;
    }
    
    public Proveedor buscar(long id){
        return entityManager().find(Proveedor.class, id);
    }
}
