package dds.gesoc.model;

import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
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
	private EntidadJuridica entidadJuridica;
	private EntidadBase entidadBase;
	private Categoria categoria;
	private BloqueoNuevosEgresos bloqueoNuevosEgresos;
	private BloqueoAgregarEntidadBase bloqueoAgregarEntidadBase;
	private Moneda pesosArgentinos;

	private DatosEgreso datosEgreso;
    @Before
    public void init() {
    	
    	pesosArgentinos = new Moneda("ARS", "Peso argentino", "$");
    	
    	burguerKing = new Proveedor("Burguer King");
    	tarjeta = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
    	efectivo = new MedioPago(TipoMedioPago.EFECTIVO);
    	lechuga = new Item("lechuga", new ValorMonetario(200.00, pesosArgentinos));
    	tomate = new Item("tomate", new ValorMonetario(150.00, pesosArgentinos));
    	cheddar = new Item("cheddar", new ValorMonetario(70.00, pesosArgentinos));
    	cebolla = new Item("cebolla", new ValorMonetario(175.00, pesosArgentinos));
    	queso = new Item("queso", new ValorMonetario(26.00, pesosArgentinos));

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
    	Assert.assertEquals(200.00, lechuga.getMonto(), 0);
    }
    
    @Test(expected = BloquearEgresoException.class)
    public void egresoBloqueadoEnEntidad(){
    	categoria.agregarReglaDeNegocio(bloqueoNuevosEgresos);
    	egreso.agregarItem(cebolla);
    	egreso.agregarItem(queso);
    	entidadJuridica.setListaEgresos(egreso);
    	//Se lanza la excepcion porque el monto del egreso es de 201, y el monto esperado de la entidad es menor (200).
    }
    
    @Test(expected = BloquearEntidadBaseEnJuridicaException.class)
    public void entidadBaseBloqueadaEnEntidadJuridica(){
    	categoria.agregarReglaDeNegocio(bloqueoAgregarEntidadBase);
    	entidadJuridica.agregarEntidadBase(entidadBase);
    	//Estamos queriendo agregar una entidad base a una juridica pero se lanza la excepcion BloquearEntidadBaseEnJuridicaException.
    }
}
