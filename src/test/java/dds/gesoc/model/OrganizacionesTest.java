package dds.gesoc.model;

import dds.gesoc.exceptions.DniOCuitInvalidoException;
import dds.gesoc.exceptions.EntidadBaseTieneEntidaJuridicaException;
import dds.gesoc.exceptions.NoClasificaComoPymeException;
import dds.gesoc.model.organizaciones.*;
import dds.gesoc.model.repositorios.RepoEntidades;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class OrganizacionesTest implements WithGlobalEntityManager {

    private static final int VENTAS_ANUALES_EMPRESA_MEDIANA1 = 425169999;
    private static final int VENTAS_ANUALES_GRAN_EMPRESA = 999999999;
    private static final int VENTAS_ANUALES_PEQUENIA_EMPRESA = 50949999;


    private Empresa miEmpresa;
    private EntidadBase entidadBase;
    private EntidadJuridica empresaDeTrajes;
    private EntidadJuridica empresaDeModa;

    //TODO: arreglar la categoria y el monto esperado, estan null y 0 respectivamente para que no tire error
    @Before
    public void init() {
        miEmpresa = new Empresa("mc Donaldo", null, 0, "Arcos Dorados SRL",
                "27-12345678-1", "Av. Corrientes 5600", new SectorServicios(), 150000);

        entidadBase = new EntidadBase("Altos pantalones", null, 0, "Taller de pantalones");
        empresaDeTrajes = new Empresa("macowins", null, 0, "Mac o wins SRL",
                "10-12345678-1", "Av. Medrano 950", new SectorComercio(), 200000);
        empresaDeModa = new Empresa("La salada", null, 0, "Salada SRL",
                "22-12345678-2", "Newton 3200", new SectorComercio(), 400000);
    }


    @Test
    public void miEmpresaEsMicro() {
        Assert.assertEquals(TipoEmpresa.MICRO, miEmpresa.getTipoEmpresa());
    }

    @Test (expected = NoClasificaComoPymeException.class)
    public void conGrandesVentasNoEsPyme() {
        miEmpresa.setVentasAnuales(VENTAS_ANUALES_GRAN_EMPRESA);

    }

    @Test
    public void sectorServiciosConVENTAS_ANUALES_PEQUENIA_EMPRESAEsPequenia() {
        miEmpresa.setVentasAnuales(VENTAS_ANUALES_PEQUENIA_EMPRESA);
        Assert.assertEquals(TipoEmpresa.PEQUENIA, miEmpresa.getTipoEmpresa());
    }

    @Test
    public void sectorServiciosConVENTAS_ANUALES_EMPRESA_MEDIANA1EsMEDIANA1() {
        miEmpresa.setVentasAnuales(VENTAS_ANUALES_EMPRESA_MEDIANA1);
        Assert.assertEquals(TipoEmpresa.MEDIANA_TRAMO1, miEmpresa.getTipoEmpresa());
    }

    @Test (expected = DniOCuitInvalidoException.class)
    public void seDetectanCuitInvalidos() {
        Empresa miEmpresa2 = new Empresa("mc Donaldo", null, 0, "Arcos Dorados SRL",
                "27-123456-1", "Av. Corrientes 5600", new SectorServicios(), 150000);
    }

    @Test (expected = EntidadBaseTieneEntidaJuridicaException.class)
    public void unaEntidadBaseNoPuedeEstarEnVariasEntidadesJuridicas() {
    	entityManager().getTransaction().begin();
    	empresaDeModa.agregarEntidadBase(entidadBase);
        RepoEntidades.getInstance().agregar(empresaDeModa);
        entityManager().getTransaction().commit();
        empresaDeTrajes.agregarEntidadBase(entidadBase);
    }

}

