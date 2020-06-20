package dds.gesoc.model.egresos;

import java.util.ArrayList;
import java.util.List;

public class RepoEgresos {

    private static RepoEgresos ourInstance;
    private List<Egreso> egresosNoValidados = new ArrayList<>();
    private List<Egreso> todosLosEgresos = new ArrayList<>();

    public static RepoEgresos getInstance() {
        if (ourInstance == null) {
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

    public void agregarEgresoNoValidado(Egreso unEgreso) {

        if (!egresosNoValidados.contains(unEgreso))
             egresosNoValidados.add(unEgreso);
    }

    private void removerEgresoNoValidado(Egreso unEgreso) {
        if(egresosNoValidados.contains(unEgreso))
        	egresosNoValidados.remove(unEgreso);
    }

    public void validarEgresos() {
        egresosNoValidados.stream().forEach(egreso -> egreso.validar());
        egresosNoValidados = (List<Egreso>) egresosNoValidados.stream().filter(egreso -> !egreso.egresoValido());
    }
}
