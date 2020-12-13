package dds.gesoc.Main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dds.gesoc.model.egresos.*;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.repositorios.RepoEgresos;
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

    /*public static void validar() {
        repo.validarEgresos();
        //System.out.println("Hola");
    }*/
    
//    public static void main(String[] args) {
//
//
//        repo = RepoEgresos.getInstance();
//
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(Main::validar, 0, 10, TimeUnit.DAYS);
//    }
}
