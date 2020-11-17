package dds.gesoc.model;

import dds.gesoc.exceptions.*;
import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.organizaciones.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	private Categoria categoriaParaJuridica;
	private Categoria categoriaParaBase;
	private ComportamientoSegunReglaDeNegocio bloqueoNuevosEgresos;
	private ComportamientoSegunReglaDeNegocio bloqueoAgregarEntidadBase;
	private ComportamientoSegunReglaDeNegocio bloqueoEntidadBaseNoPuedePertenecerAJuridica;
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
        
        categoriaParaJuridica = new Categoria("ONG");
        categoriaParaBase = new Categoria("ONG");

        entidadJuridica = new EntidadJuridica("Hermanos Scott", categoriaParaJuridica, 200.0,"IBM","20-42498956-2","6600", 0);

        entidadBase = new EntidadBase("Hermanos perez", categoriaParaBase, 200.0, "Venta de autos");
        bloqueoNuevosEgresos = new BloqueoNuevosEgresos();
        bloqueoAgregarEntidadBase = new BloqueoEntidadJuridicaNoAceptaEntidadesBase();
        bloqueoEntidadBaseNoPuedePertenecerAJuridica = new BloqueoEntidadBaseNoPuedePertenecerAJuridicas();
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

    //TERCERA ENTREGA: Tests de categorías de entidades

    @Test(expected = BloqueoEgresoExcedeMontoMaxException.class)
    public void egresoBloqueadoEnEntidad(){
    	categoriaParaJuridica.agregarReglaDeNegocio(ReglaDeNegocio.ACEPTACION_NUEVOS_EGRESOS);
    	egreso.agregarItem(cebolla);
    	egreso.agregarItem(queso);
    	entidadJuridica.agregarEgreso(egreso);
    	//Se lanza la excepcion porque el monto del egreso es de 201, y el monto esperado de la entidad es menor (200).
    }
    
    @Test(expected = BloqueoJuridicaNoAceptaEntBasesException.class)
    public void entidadJuridicaNoPuedeAceptarEntidadesBasicasSegunCategoria(){
    	categoriaParaJuridica.agregarReglaDeNegocio(ReglaDeNegocio.ENT_JURIDICA_AGREGA_ENT_BASES);
    	entidadJuridica.agregarEntidadBase(entidadBase);
    	//La entidad jurídica tiene prohibido aceptar entidades base
    }

    @Test (expected = BloqueoEntidadBaseNoPuedePertenecerAJuridicasException.class)
	public void entidadBasicaNoPuedePertenecerAJuridicas() {
    	categoriaParaBase.agregarReglaDeNegocio(ReglaDeNegocio.ENT_BASE_FORMA_PARTE_ENT_JURIDICA);
    	entidadJuridica.agregarEntidadBase(entidadBase);
    	//La entidad base que quiero agregar en la entdad jurídica tiene prohibido pertenecer a entidades jurídicas
	}

}
