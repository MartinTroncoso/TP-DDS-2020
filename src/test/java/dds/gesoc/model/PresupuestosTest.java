package dds.gesoc.model;

import dds.gesoc.exceptions.PresupuestoSinEgresoAsociadoException;
import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PresupuestosTest {

    private Presupuesto presupuestoJardinBarato;
    private Presupuesto presupuestoCasa9DeJulio;
    private Presupuesto presupuestoGarbarino;
    private Proveedor proveedorA;
    private Proveedor proveedorBarato;
    private Proveedor proveedorC;
    private Documento presupuestoDocumento;
    private Documento documentoFactura;
    private DatosEgreso datosEgreso;
    private MedioPago unMedioDePago;
    private Egreso egreso;
    private Egreso egresoInvalido;
    private Moneda moneda;

    private Item parrillaCara;
    private Item parrillaMedia;
    private Item parrillaEconomica;

    private Empresa empresaEjemplo;
    private Usuario primerRevisor;
    private Usuario segundoRevisor;

    @Before
    public void init() {
    	
        proveedorA = new Proveedor("Casa 9 de Julio");
        proveedorBarato = new Proveedor("jardineria");
        proveedorC = new Proveedor("garbarino");
        presupuestoDocumento = new Documento("Presupuesto", 44);
        documentoFactura = new Documento("A", 55);
        unMedioDePago = new MedioPago(TipoMedioPago.EFECTIVO);
        datosEgreso = new DatosEgreso(proveedorA, documentoFactura,unMedioDePago);

        moneda = new Moneda("ARS", "plata", "$");

        egreso = new Egreso(datosEgreso, moneda, 2, CriterioSeleccionProveedor.MENOR_VALOR);
        egresoInvalido = new Egreso(datosEgreso, moneda, 5, CriterioSeleccionProveedor.MENOR_VALOR);

        presupuestoCasa9DeJulio = new Presupuesto(proveedorA, presupuestoDocumento, moneda, "Vende cerca", egreso);
        presupuestoJardinBarato = new Presupuesto(proveedorBarato, presupuestoDocumento, moneda, "Vende barato", egreso);
        presupuestoGarbarino = new Presupuesto(proveedorC, presupuestoDocumento, moneda, "Vende caro", egreso);


        parrillaCara = new Item("parrilla", new ValorMonetario(35000, moneda));
        parrillaEconomica = new Item("parilla", new ValorMonetario(1200, moneda));
        parrillaMedia = new Item("parrilla", new ValorMonetario(12500, moneda));
        presupuestoCasa9DeJulio.agregarItem(parrillaMedia);
        presupuestoJardinBarato.agregarItem(parrillaEconomica);
        presupuestoGarbarino.agregarItem(parrillaCara);


        //TODO: arreglar la categoria y el monto esperado, estan null y 0 respectivamente para que no tire error
        //Usuarios
        empresaEjemplo = new Empresa("mc Donaldo", null, 0, "Arcos Dorados SRL", "27-12345678-1",
                "Av. Corrientes 5600", new SectorServicios(), 150000);
        primerRevisor = new Usuario("juancito", "12Q4ldldldld", TipoUsuario.ESTANDAR, empresaEjemplo);
        segundoRevisor = new Usuario("maarilulis", "ppp1utututut", TipoUsuario.ESTANDAR, empresaEjemplo);
    }


    //2. Cada presupuesto debe estar asociado obligatoriamente a un ​egreso​
    @Test (expected = PresupuestoSinEgresoAsociadoException.class)
    public void noSePuedenCrearPresupuestosSinEgresoAsociado() {
        new Presupuesto(proveedorA, presupuestoDocumento, moneda, "Oferta muebles de jardin", null);
    }

    //Si la compra requiere presupuestos se debe verificar que
    //efectivamente se encuentran cargada la cantidad indicada

    @Test
    public void compraCumpleCantidadMinimaPresupuestos() {
        //Cuando se crea un presupuesto, este es agregado a la lista de presupuestos del egreso
        Assert.assertTrue(egreso.tieneCantidadMinimaDePresupuestos());
    }

    @Test
    public void compraNoCumpleConCantidadMinimaPresupuestos() {
        Assert.assertFalse(egresoInvalido.tieneCantidadMinimaDePresupuestos());
    }

    // Si la compra requiere presupuestos se debe verificar que la compra haya sido realizada en
    //base a alguno de esos presupuestos

    @Test
    public void compraRealizadaSegunPresupuesto() {
        //El egreso tiene los mismos items que el presupuestoCasa9DeJulio: proveedorA, parrillaMedia y mismo valor total
        egreso.agregarItem(parrillaMedia);
        egreso.setMiProveedor(proveedorA);

        Assert.assertTrue(egreso.compraRealizadaSegunAlgunPresupuesto());
    }

    @Test
    public void compraInvalidaSiNoSeRealizoEnBaseAPresupuesto() {
        new Presupuesto(proveedorA, presupuestoDocumento, moneda,"Oferta", egresoInvalido);
        egresoInvalido.agregarItem(new Item("Nada que ver", new ValorMonetario(8000, moneda)));
        egresoInvalido.setMiProveedor(new Proveedor("solo prueba SA"));

        Assert.assertFalse(egresoInvalido.compraRealizadaSegunAlgunPresupuesto());
    }

    // debe validarse que, entre
    //los presupuestos cargados en el sistema, se haya seleccionado el de menor valor.

    @Test
    public void egresoSegunMejorPresupuesto() {
        egreso.setMiProveedor(proveedorBarato);
        Assert.assertTrue(egreso.eligioProveedorSegunCriterio());
    }

    @Test
    public void egresoConProveedorQueNoCumpleCriterio() {
        new Presupuesto(proveedorA, presupuestoDocumento, moneda, "Oferta", egresoInvalido);
        egresoInvalido.setMiProveedor(new Proveedor("Otro que nada que ver SA"));
        Assert.assertFalse(egresoInvalido.eligioProveedorSegunCriterio());
    }

    //Los resultados de la validación deberán ser vistos por el sistema por parte de algunos usuarios

    @Test
    public void usuarioPuedeDarseDeAltaComoRevisor() {
        egreso.agregarUsuarioRevisor(primerRevisor);
        egreso.agregarUsuarioRevisor(segundoRevisor);
        Assert.assertEquals(2.0, egreso.getUsuariosRevisores().size(), 0.0);
    }

    @Test
    public void usuarioSuscritoRecibeNotificacionesCuandoElEgresoSeValida() {
        Assert.assertEquals(primerRevisor.mensajesEnBandeja(), 0);
        egreso.agregarUsuarioRevisor(primerRevisor);
        egreso.validar();
        Assert.assertEquals(primerRevisor.mensajesEnBandeja(), 1);


    }



}
