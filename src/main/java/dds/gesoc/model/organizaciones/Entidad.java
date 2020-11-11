
package dds.gesoc.model.organizaciones;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.model.RepoEntidades.RepoEntidades;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.geografia.ValorMonetario;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "entidad")
@DiscriminatorColumn(name = "tipo_entidad", length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Entidad extends EntidadPersistente{
	
	@Column
    private String nombreFicticio;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    protected Categoria categoria;
    
    @Column
    protected double montoEsperado;
    
    @OneToMany
    @JoinColumn(name = "entidad_id")
    protected List<Egreso> egresosEntidad = new ArrayList<>();

    public Entidad() {
    	//RepoEntidades.getInstance().agregarEntidad(this);
    }

    protected Entidad(String nombreFicticio, Categoria categoria, double montoEsperado) {
        setNombreFicticio(nombreFicticio);
        setCategoria(categoria);
        setMontoEsperado(montoEsperado);
        //RepoEntidades.getInstance().agregarEntidad(this);
    }

	public void setMontoEsperado(double montoEsperado) {
		this.montoEsperado = montoEsperado;
	}

	public void setNombreFicticio(String nombreFicticio) {
		this.nombreFicticio = nombreFicticio;
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
        setCategoria(unaCategoria);
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


