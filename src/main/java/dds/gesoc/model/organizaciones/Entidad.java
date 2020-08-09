
package dds.gesoc.model.organizaciones;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.geografia.ValorMonetario;

import java.util.*;
import java.util.stream.Collectors;


public abstract class Entidad {

    private String nombreFicticio;
    protected Categoria categoria;
    protected double montoEsperado;
    protected List<Egreso> egresosEntidad = new ArrayList<>();


    protected Entidad(String nombreFicticio, Categoria categoria, double montoEsperado) {
        this.nombreFicticio = nombreFicticio;
        this.categoria = categoria;
        this.montoEsperado = montoEsperado;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void agregarEgreso(Egreso unEgreso) {

        if(categoria != null)
            categoria.aplicarReglasDeNegocio(this, TipoRegla.ACEPTACION_NUEVOS_EGRESOS, this.getMontoEsperado(), unEgreso);
    	egresosEntidad.add(unEgreso);
    }

    public Map<String, List<Egreso>> generarReporteEgresosPorEtiqueta() {

        Map<String, List<Egreso>> egresosPorEtiqueta = new HashMap<>();

        for(String unaEtiqueta : this.todasLasEtiquetasDeEgresos()) {
            egresosPorEtiqueta.put(unaEtiqueta, this.egresosEntidad.stream().filter(egreso -> egreso.getEtiquetas().contains(unaEtiqueta)).collect(Collectors.toList()));
        }

        return egresosPorEtiqueta;
        //return egresosEntidad.stream().collect(Collectors.groupingBy(Egreso::getEtiqueta));
    }

    private Set<String> todasLasEtiquetasDeEgresos() {
        return egresosEntidad.stream().map(Egreso::getEtiquetas).flatMap(Collection::stream).collect(Collectors.toSet());
    //etiquetas -> etiquetas.stream() ==  Collection::stream
    }


    public Map<String, ValorMonetario> generarReporteMontosTotalesPorEtiqueta() {

        return generarReporteEgresosPorEtiqueta().entrySet()
                .stream().collect(Collectors.toMap(tupla -> tupla.getKey(), tupla -> valorTotalParaListaDeEgresos(tupla.getValue())));
    }


    private ValorMonetario valorTotalParaListaDeEgresos(List<Egreso> listaEgresos) {
        double valorTotal = (Double) listaEgresos.stream().mapToDouble(egreso -> egreso.valorTotalEnMoneda(null)).sum();

        //TODO Acá supongo que todos los items están en la misma moneda. Corregir si se pueden tener egresos en distintas monedas
        //TODO usar una moneda por defecto
        return new ValorMonetario(valorTotal, null);
    }


    public Categoria getCategoria() {
        return categoria;
    }


    public void setCategoria(Categoria unaCategoria) {
        this.categoria = unaCategoria;
    }

    public List<Egreso> getEgresos() {
        return egresosEntidad;
    }

    public double getMontosTotales() {
        return egresosEntidad.stream().mapToDouble(e -> e.valorTotal().getMonto()).sum();
    }

    public double getMontoEsperado() {
        return montoEsperado;
    }


}


