package dds.gesoc.model;

import dds.gesoc.model.egresos.*;
import dds.gesoc.model.organizaciones.BloqueoAgregarEntidadBase;
import dds.gesoc.model.organizaciones.BloqueoNuevosEgresos;
import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.gesoc.exceptions.BloquearEgresoException;
import dds.gesoc.exceptions.BloquearEntidadBaseEnJuridicaException;
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
	private Item queso;
	private static String DNI_CERO = "0";
	private static String DNI_NEGATIVO = "-1";
	private static String DNI_CON_NUEVE_CIFRAS = "100000000";
	private static String CUIT_INCORRECTO = "20-12345-678-0";
	private Entidad entidadJuridica;
	private EntidadBase entidadBase;
	private Categoria categoria;
	private BloqueoNuevosEgresos bloqueoNuevosEgresos;
	private BloqueoAgregarEntidadBase bloqueoAgregarEntidadBase;

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
    	queso = new Item("queso",26.00);

    	datosEgreso = new DatosEgreso(burguerKing, tarjeta);

        egreso = new Egreso(datosEgreso, null, 0, null);
        
        categoria = new Categoria("ONG");
        entidadJuridica = new EntidadJuridica("Hermanos Scott",categoria, 200.0,"IBM","20-42498956-2","6600");
        entidadBase = new EntidadBase("Hermanos perez",categoria, 200.0, "Venta de autos");
        bloqueoNuevosEgresos = new BloqueoNuevosEgresos(egreso);
        bloqueoAgregarEntidadBase = new BloqueoAgregarEntidadBase(entidadBase);
    }
    
    @Test
    public void aniadirItems() {
    	egreso.agregarItem(lechuga);
    	egreso.agregarItem(cheddar);
    	Assert.assertEquals(270.00, egreso.valorTotal().getMonto(), 0);
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
    
    @Test(expected = BloquearEgresoException.class)
    public void egresoBloqueadoException(){
    	categoria.agregarReglaDeNegocio(bloqueoNuevosEgresos);
    	egreso.agregarItem(cebolla);
    	egreso.agregarItem(queso);
    	entidadJuridica.agregarEgreso(egreso);
    }
}
