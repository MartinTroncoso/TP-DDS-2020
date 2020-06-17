package dds.gesoc.model.egresos;

import dds.gesoc.model.organizaciones.Entidad;

import java.util.ArrayList;
import java.util.List;

public class RepoEgresos {

    private static RepoEgresos ourInstance;
    private List<Egreso> egresosNoValidados = new ArrayList<>();

    public static RepoEgresos getInstance() {
        if (ourInstance == null) {
            ourInstance = new RepoEgresos();
        }
        return ourInstance;
    }

    private RepoEgresos() {
    }

    public void agregarEgresoNoValidado(Egreso unEgreso) {
        egresosNoValidados.add(unEgreso);
    }



}
