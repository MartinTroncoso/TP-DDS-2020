package dds.gesoc.model.organizaciones;

import dds.gesoc.exceptions.EtiquetaNoValidaException;
import dds.gesoc.exceptions.EtiquetaYaExistenteException;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Etiqueta;

import java.util.ArrayList;
import java.util.List;

public abstract class Entidad {

    private String nombreFicticio;
    protected Categoria categoria;
    protected double montoEsperado;
    protected List<Egreso> egresosEntidad = new ArrayList<>();
    private List<Etiqueta> etiquetasEgresos = new ArrayList<>();

    protected Entidad (String nombreFicticio, Categoria categoria, double montoEsperado) {
        this.nombreFicticio = nombreFicticio;
        this.categoria = categoria;
        this.montoEsperado = montoEsperado;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }
    
    public void agregarEtiqueta(Etiqueta unaEtiqueta) {
        etiquetasEgresos.add(unaEtiqueta);
    }

    public void eliminarEtiqueta(Etiqueta unaEtiqueta) {
        etiquetasEgresos.remove(unaEtiqueta);
        egresosEntidad.stream()
        			  .filter(egreso -> egreso.getEtiqueta().equals(unaEtiqueta))
        			  .forEach(egreso -> egreso.borrarEtiqueta());
    }
    
    public Categoria getCategoria() {
    	return categoria;
    }
    
    public List<Egreso> getEgresos(){
    	return egresosEntidad;
    }
    
    public double getMontosTotales(){
    	return egresosEntidad.stream().mapToDouble(e -> e.valorTotal().getMonto()).sum();
    }
    
    public double getMontoEsperado() {
    	return montoEsperado;
    }

    public void agregarEgreso(Egreso unEgreso) {
	//BLOQUEOS CATEGORIA
	categoria.aplicarReglasDeNegocio(this);
        if (!egresoConEqituetaValida(unEgreso))
            throw new EtiquetaNoValidaException("La etiqueta del egreso no pertenece a esta entidad");
        egresosEntidad.add(unEgreso);
    }

    private boolean egresoConEqituetaValida(Egreso unEgreso) {
        return unEgreso.getEtiqueta() == null || etiquetasEgresos.contains(unEgreso.getEtiqueta());
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
}
