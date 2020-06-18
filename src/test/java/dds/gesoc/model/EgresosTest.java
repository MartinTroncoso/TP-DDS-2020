package dds.gesoc.model;

import dds.gesoc.model.egresos.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.gesoc.exceptions.DniOCuitInvalidoException;
import dds.gesoc.exceptions.ValidarTarjetaException;

public class EgresosTest {
	private Egreso egreso;
	private Proveedor burguerKing;
	private MedioPago tarjeta;
	private MedioPago efectivo;
	private Item lechuga;
	private Item tomate;
	private Item cheddar;
	private Item cebolla;
	private static String DNI_CERO = "0";
	private static String DNI_NEGATIVO = "-1";
	private static String DNI_CON_NUEVE_CIFRAS = "100000000";
	private static String CUIT_INCORRECTO = "20-12345-678-0";

	private DatosEgreso datosEgreso;
    @Before
    public void init() {
    	
    	burguerKing = new Proveedor("Burguer King");
    	tarjeta = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
    	efectivo = new MedioPago(TipoMedioPago.EFECTIVO);
    	lechuga = new Item("lechuga", 200.00);
    	tomate = new Item("tomate", 150.00);
    	cheddar = new Item("cheddar", 70.00);
    	cebolla = new Item("cebolla", 175.00);

    	datosEgreso = new DatosEgreso(burguerKing, tarjeta);

        egreso = new Egreso(datosEgreso, null, 0, null);
    }
    
    @Test
    public void aniadirItems() {
    	egreso.agregarItem(lechuga);
    	egreso.agregarItem(cheddar);
    	Assert.assertEquals(270.00, egreso.valorTotal().getCantidad(), 0);
    }
    
    @Test(expected = DniOCuitInvalidoException.class)
    public void ingresandoDniCero() {
    	burguerKing.establecerDniOCuit(DNI_CERO);
    }
    
    @Test(expected = DniOCuitInvalidoException.class)
    public void ingresandoDniNegativo() {
    	burguerKing.establecerDniOCuit(DNI_NEGATIVO);
    }
    
    @Test(expected = DniOCuitInvalidoException.class)
    public void ingresandoDniConNueveCifrasOMas() {
    	burguerKing.establecerDniOCuit(DNI_CON_NUEVE_CIFRAS);
    }
    
    @Test(expected = DniOCuitInvalidoException.class)
    public void ingresandoCuitIncorrecto() {
    	burguerKing.establecerDniOCuit(CUIT_INCORRECTO);
    }
    
    @Test(expected = ValidarTarjetaException.class)
    public void ingresandoNroDeTarjetaEnUnMedioDePagoEnEfectivo() {
    	efectivo.establecerNroTarjeta("1111222233334444");
    }
    
    @Test(expected = ValidarTarjetaException.class)
    public void ingresandoNroDeTarjetaIncorrecto() {
    	efectivo.establecerNroTarjeta("1111-2222-3333-4444");
    }
    
    @Test
    public void sabiendoElValorDeUnItem() {
    	Assert.assertEquals(200.00, lechuga.getValor(), 0);
    }
}
