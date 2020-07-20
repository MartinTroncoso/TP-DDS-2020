package dds.gesoc.Main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;

public class Main {

    private static Presupuesto presupuestoJardinBarato;
    private static Presupuesto presupuestoCasa9DeJulio;
    private static Presupuesto presupuestoGarbarino;
    private static Proveedor proveedorA;
    private static Proveedor proveedorBarato;
    private static Proveedor proveedorC;
    private static Documento presupuestoDocumento;
    private static Documento documentoFactura;
    private static DatosEgreso datosEgreso;
    private static MedioPago unMedioDePago;
    private static Egreso egreso;
    private static Egreso egresoInvalido;
    private static Moneda moneda;

    private static Item parrillaCara;
    private static Item parrillaMedia;
    private static Item parrillaEconomica;

    private static Empresa empresaEjemplo;
    private static Usuario primerRevisor;
    private static Usuario segundoRevisor;
    
    private static RepoEgresos repo;

    public static void validar() {
        repo.validarEgresos();
        //System.out.println("Hola");
    }
    
    public static void main(String[] args) {

        /*proveedorA = new Proveedor("Casa 9 de Julio");
        proveedorBarato = new Proveedor("jardineria");
        proveedorC = new Proveedor("garbarino");
        presupuestoDocumento = new Documento("Presupuesto", 44);
        documentoFactura = new Documento("A", 55);
        unMedioDePago = new MedioPago(TipoMedioPago.EFECTIVO);
        datosEgreso = new DatosEgreso(proveedorA, documentoFactura,unMedioDePago);

        moneda = new Moneda("ARS", "plata", "$");

        egreso = new Egreso(datosEgreso, moneda, 2, new CriterioSeleccionProveedorMenorValor());
        egresoInvalido = new Egreso(datosEgreso, moneda, 5, new CriterioSeleccionProveedorMenorValor());

        presupuestoCasa9DeJulio = new Presupuesto(proveedorA, presupuestoDocumento, moneda, "Vende cerca", egreso);
        presupuestoJardinBarato = new Presupuesto(proveedorBarato, presupuestoDocumento, moneda, "Vende barato", egreso);
        presupuestoGarbarino = new Presupuesto(proveedorC, presupuestoDocumento, moneda, "Vende caro", egreso);


        parrillaCara = new Item("parrilla", 35000);
        parrillaEconomica = new Item("parilla", 1200);
        parrillaMedia = new Item("parrilla", 12500);
        presupuestoCasa9DeJulio.agregarItem(parrillaMedia);
        presupuestoJardinBarato.agregarItem(parrillaEconomica);
        presupuestoGarbarino.agregarItem(parrillaCara);



        //Usuarios
        empresaEjemplo = new Empresa("mc Donaldo", "Arcos Dorados SRL", "27-12345678-1",
                "Av. Corrientes 5600", new SectorServicios(), 150000);
        primerRevisor = new Usuario("juancito", "12Q4ldldldld", TipoUsuario.ESTANDAR, empresaEjemplo);
        segundoRevisor = new Usuario("maarilulis", "ppp1utututut", TipoUsuario.ESTANDAR, empresaEjemplo);

        //while() {
        //}
        */
        repo = RepoEgresos.getInstance();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(Main::validar, 0, 10, TimeUnit.DAYS);
    }
}
