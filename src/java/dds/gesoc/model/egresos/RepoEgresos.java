package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepoEgresos implements WithGlobalEntityManager{

    private static RepoEgresos ourInstance;
    private List<Egreso> todosLosEgresos = new ArrayList<>();

    public static RepoEgresos getInstance() {
        if (ourInstance == null){
            ourInstance = new RepoEgresos();
        }
        return ourInstance;
    }

    private RepoEgresos() {
    
    }

    public void agregarEgresoNuevo(Egreso unEgreso) {
        todosLosEgresos.add(unEgreso);
    }

    public void removerEgreso(Egreso unEgreso) {
        todosLosEgresos.remove(unEgreso);
    }

    public List<Egreso> egresosNoValidados() {
		return todosLosEgresos.stream().filter(e -> !e.isValido()).collect(Collectors.toList());
    }
    
    public void validarEgresos() {
    	this.egresosNoValidados().forEach(e -> e.validar());
    }
    
    public List<Egreso> getEgresos(){
    	return todosLosEgresos;
    }
    
    public Egreso buscar(long id){
        return entityManager().find(Egreso.class, id);
    }
    
    public Egreso get(int index) {
    	return todosLosEgresos.get(index);
    }
}