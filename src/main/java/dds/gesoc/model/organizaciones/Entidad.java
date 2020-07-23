
package dds.gesoc.model.organizaciones;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Etiqueta;
import dds.gesoc.model.geografia.ValorMonetario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        categoria.aplicarReglasDeNegocio(this);
    	egresosEntidad.add(unEgreso);
    }

    public Map<Etiqueta, List<Egreso>> generarReporteEgresosPorEtiqueta() {
        return egresosEntidad.stream().collect(Collectors.groupingBy(Egreso::getEtiqueta));
    }

//TODO la clase es abstracta porque no quiero instancias de esta, sino de sus subclases, pero no tengo métodos abstractos. Preguntar


    public Map<Etiqueta, ValorMonetario> generarReporteMontosTotalesPorEtiqueta() {

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


