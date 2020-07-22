package dds.gesoc.model;

import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import jdk.internal.dynalink.linker.LinkerServices;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EtiquetasTest {

    private Empresa miEmpresa;
    private Etiqueta amoblamientoEt;
    private Etiqueta ingredientesEt;
    private Etiqueta proveedorEt;

    private MedioPago tarjeta;
    private MedioPago efectivo;
    private Item lechuga;
    private Item tomate;
    private Item huevo;
    private Item carne;
    private Item mesa;
    private Item sillas;

    Egreso egresoIngredientes;
    Egreso egresoIngredientes2;
    Egreso egresoMuebles;

    private Proveedor rolando;

    DatosEgreso datosEgreso;

    @Before
    public void init() {
        miEmpresa = new Empresa("mc Donaldo", "Arcos Dorados SRL",
                "27-12345678-1", "Av. Corrientes 5600", new SectorServicios(), 150000);

        proveedorEt = new Etiqueta("Rolando");
        amoblamientoEt = new Etiqueta("Amoblamiento");
        ingredientesEt = new Etiqueta("Ingredentes");


        rolando = new Proveedor("Rolando srl");
        tarjeta = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
        efectivo = new MedioPago(TipoMedioPago.EFECTIVO);
        lechuga = new Item("lechuga", 200.00);
        tomate = new Item("tomate", 150.00);
        huevo = new Item("huevo", 50.00);
        carne = new Item("carne", 150.00);

        mesa = new Item("mesa", 70.00);
        sillas = new Item("sillas", 175.00);

        datosEgreso = new DatosEgreso(rolando, tarjeta);

        egresoIngredientes = new Egreso(datosEgreso, null, 0, null);
        egresoIngredientes2 = new Egreso(datosEgreso, null, 0, null);
        egresoIngredientes.agregarItem(lechuga);
        egresoIngredientes.agregarItem(tomate);
        egresoIngredientes2.agregarItem(huevo);
        egresoIngredientes2.agregarItem(carne);
        egresoIngredientes.setEtiqueta(ingredientesEt);
        egresoIngredientes2.setEtiqueta(ingredientesEt);

        egresoMuebles = new Egreso(datosEgreso, null, 0, null);
        egresoMuebles.agregarItem(mesa);
        egresoMuebles.agregarItem(sillas);
        egresoMuebles.setEtiqueta(amoblamientoEt);


        miEmpresa.agregarEgreso(egresoIngredientes);
        miEmpresa.agregarEgreso(egresoIngredientes2);
        miEmpresa.agregarEgreso(egresoMuebles);


    }


    @Test
    public void seGeneraUnReporteTotalesSegunEtiqueta() {
       Map<Etiqueta, ValorMonetario> reporte = miEmpresa.generarReporteMontosTotalesPorEtiqueta();
       Iterator iterable = reporte.keySet().iterator();

       while(iterable.hasNext()) {
           Etiqueta etiqueta = (Etiqueta) iterable.next();
           System.out.println( "Etiqueta: " + etiqueta.getNombre() + "  " + "- Monto total: "+ reporte.get(etiqueta).getCantidad());

       }
    }

    @Test
    public void seGeneraUnReporteEgresosSegunEtiqueta() {
        Map<Etiqueta, List<Egreso>> reporte = miEmpresa.generarReporteEgresosPorEtiqueta();

        Iterator iterable = reporte.keySet().iterator();

        while(iterable.hasNext()) {
            Etiqueta etiqueta = (Etiqueta) iterable.next();
            System.out.println( "Etiqueta: " + etiqueta.getNombre() + "  " + "- Egresos: "+ reporte.get(etiqueta));
        }
    }

}
