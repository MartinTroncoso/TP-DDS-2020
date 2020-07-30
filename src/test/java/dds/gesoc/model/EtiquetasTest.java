package dds.gesoc.model;

import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EtiquetasTest {

    private Empresa miEmpresa;

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
	private Moneda pesosArgentinos;

	private static String AMOBLAMIENTO = "amoblamiento";
	private static String CARNICERIA = "carniceria";
	private static String INGREDIENTES = "ingredientes";


    @Before
    public void init() {
    	
    	pesosArgentinos = new Moneda("ARS", "Peso argentino", "$");
    	
        miEmpresa = new Empresa("mc Donaldo", null, 10000000,"Arcos Dorados SRL",
                "27-12345678-1", "Av. Corrientes 5600", new SectorServicios(), 150000);


        rolando = new Proveedor("Rolando srl");
        tarjeta = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
        efectivo = new MedioPago(TipoMedioPago.EFECTIVO);
        lechuga = new Item("lechuga", new ValorMonetario(200.00, pesosArgentinos));
        tomate = new Item("tomate", new ValorMonetario(150.00, pesosArgentinos));
        huevo = new Item("huevo", new ValorMonetario(50.00, pesosArgentinos));
        carne = new Item("carne", new ValorMonetario(150.00, pesosArgentinos));

        mesa = new Item("mesa", new ValorMonetario(70.00, pesosArgentinos));
        sillas = new Item("sillas", new ValorMonetario(175.00, pesosArgentinos));

        datosEgreso = new DatosEgreso(rolando, tarjeta);

        egresoIngredientes = new Egreso(datosEgreso, null, 0, null);
        egresoIngredientes2 = new Egreso(datosEgreso, null, 0, null);
        egresoIngredientes.agregarItem(lechuga);
        egresoIngredientes.agregarItem(tomate);
        egresoIngredientes2.agregarItem(huevo);
        egresoIngredientes2.agregarItem(carne);
        egresoIngredientes.agregarEtiqueta(INGREDIENTES);
        egresoIngredientes2.agregarEtiqueta(INGREDIENTES);
        egresoIngredientes2.agregarEtiqueta(CARNICERIA);


        egresoMuebles = new Egreso(datosEgreso, null, 0, null);
        egresoMuebles.agregarItem(mesa);
        egresoMuebles.agregarItem(sillas);
        egresoMuebles.agregarEtiqueta(AMOBLAMIENTO);


        miEmpresa.agregarEgreso(egresoIngredientes);
        miEmpresa.agregarEgreso(egresoIngredientes2);
        miEmpresa.agregarEgreso(egresoMuebles);


    }


    @Test
    public void seGeneraUnReporteTotalesSegunEtiqueta() {
       Map<String, ValorMonetario> reporte = miEmpresa.generarReporteMontosTotalesPorEtiqueta();

        Assert.assertEquals(245.0, reporte.get(AMOBLAMIENTO.toLowerCase()).getMonto(), 0.0);
        Assert.assertEquals(200.0, reporte.get(CARNICERIA.toLowerCase()).getMonto(), 0.0);
        Assert.assertEquals(550.0, reporte.get(INGREDIENTES.toLowerCase()).getMonto(), 0.0);
    }

    @Test
    public void seGeneraUnReporteEgresosSegunEtiqueta() {
        Map<String, List<Egreso>> reporte = miEmpresa.generarReporteEgresosPorEtiqueta();

        Assert.assertEquals(1, reporte.get(AMOBLAMIENTO.toLowerCase()).size());
        Assert.assertEquals(1, reporte.get(CARNICERIA.toLowerCase()).size());
        Assert.assertEquals(2, reporte.get(INGREDIENTES.toLowerCase()).size());
    }

}
