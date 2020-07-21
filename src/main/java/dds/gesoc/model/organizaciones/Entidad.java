package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.EtiquetaNoValidaException;
import dds.gesoc.exceptions.EtiquetaYaExistenteException;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Etiqueta;

import java.util.ArrayList;
import java.util.List;

public abstract class Entidad {
    //TODO la clase es abstracta porque no quiero instancias de esta, sino de sus subclases, pero no tengo métodos abstractos. Preguntar

    private String nombreFicticio;
    private List<Etiqueta> etiquetasEgresos = new ArrayList<>();
    protected List<Egreso> egresosEntidad = new ArrayList<>();

    protected Entidad (String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void agregarEtiqueta(Etiqueta unaEtiqueta) {
        etiquetasEgresos.add(unaEtiqueta);
    }

    public void eliminarEtiqueta(Etiqueta unaEtiqueta) {
        etiquetasEgresos.remove(unaEtiqueta);
        egresosEntidad.stream().filter(egreso -> egreso.getEtiqueta().equals(unaEtiqueta)).forEach(egreso -> egreso.borrarEtiqueta());
    }

    public void agregarEgreso(Egreso unEgreso) {
        if (!egresoConEqituetaValida(unEgreso))
            throw new EtiquetaNoValidaException("La etiqueta del egreso no pertenece a esta entidad");
        egresosEntidad.add(unEgreso);
    }

    private boolean egresoConEqituetaValida(Egreso unEgreso) {
        return  unEgreso.getEtiqueta() ==  null || etiquetasEgresos.contains(unEgreso.getEtiqueta());
    }
/*

    public void agregarEgreso(Egreso unEgreso, Etiqueta unaEtiqueta) {
        unEgreso.setEtiqueta(unaEtiqueta);
        etiquetasEgresos.add(unaEtiqueta);
        egresosEntidad.add(unEgreso);
    }
*/

    public void crearEtiquetasParaEgresos(String nombre) {
        if (etiquetasEgresos.stream().anyMatch(etiqueta -> etiqueta.getNombre().equalsIgnoreCase(nombre)))
            throw new EtiquetaYaExistenteException("Ya hay una etiqueta con este nombre en la entidad");

        etiquetasEgresos.add(new Etiqueta(nombre));
    }

    public

}
