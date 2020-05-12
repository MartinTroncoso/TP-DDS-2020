package dds.gesoc.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.gesoc.exceptions.CuitInvalidoException;
import dds.gesoc.exceptions.DniIncorrectoException;
import dds.gesoc.exceptions.ValidarTarjetaException;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.egresos.TipoMedioPago;

public class EgresosTest {
	private Egreso egreso;
	private Proveedor burguerKing;
	private MedioPago tarjeta;
	private MedioPago efectivo;
	private Item lechuga;
	private Item tomate;
	private Item cheddar;
	private Item cebolla;

    @Before
    public void init() {
    	
    	burguerKing = new Proveedor("Burguer King");
    	tarjeta = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
    	efectivo = new MedioPago(TipoMedioPago.EFECTIVO);
    	lechuga = new Item("lechuga", 200.00);
    	tomate = new Item("tomate", 150.00);
    	cheddar = new Item("cheddar", 70.00);
    	cebolla = new Item("cebolla", 175.00);
    	
        egreso = new Egreso(burguerKing, tarjeta);
    }
    
    @Test
    public void aniadirItems() {
    	egreso.aniadirItem(lechuga);
    	egreso.aniadirItem(cheddar);
    	Assert.assertEquals(270.00, egreso.valorTotal(), 0);
    }
    
    @Test(expected = DniIncorrectoException.class)
    public void ingresandoDniCero() {
    	burguerKing.establecerDNI(0);
    }
    
    @Test(expected = DniIncorrectoException.class)
    public void ingresandoDniNegativo() {
    	burguerKing.establecerDNI(-1);
    }
    
    @Test(expected = DniIncorrectoException.class)
    public void ingresandoDniConNueveCifrasOMas() {
    	burguerKing.establecerDNI(100000000);
    }
    
    @Test(expected = CuitInvalidoException.class)
    public void ingresandoCuitIncorrecto() {
    	burguerKing.establecerCuit("20-12345-678-0");
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
