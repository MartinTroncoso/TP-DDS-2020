package dds.gesoc.model;

import dds.gesoc.exceptions.CuitInvalidoException;
import dds.gesoc.exceptions.NoClasificaComoPymeException;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.organizaciones.TipoEmpresa;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrganizacionesTest {

    private Empresa miEmpresa;


    @Before
    public void init() {
        miEmpresa = new Empresa("mc Donaldo", "Arcos Dorados SRL",
                "27-12345678-1", "Av. Corrientes 5600", new SectorServicios(), 150000);
    }


    @Test
    public void miEmpresaEsMicro() {
        Assert.assertEquals(TipoEmpresa.MICRO, miEmpresa.getTipoEmpresa());
    }

    @Test (expected = NoClasificaComoPymeException.class)
    public void conGrandesVentasNoEsPyme() {
        miEmpresa.setVentasAnuales(999999999); //TODO Solo se trabajan com pymes

    }


    @Test
    public void sectorServiciosConVentas50949999DeEsPequenia() {
        miEmpresa.setVentasAnuales(50949999); //TODO Solo se trabajan com pymes

    }


    @Test
    public void sectorServiciosConVentas425169999DeEsMEDIANA1() {
        miEmpresa.setVentasAnuales(425169999); //TODO Solo se trabajan com pymes
        Assert.assertEquals(TipoEmpresa.MEDIANA_TRAMO1, miEmpresa.getTipoEmpresa());
    }

    @Test (expected = CuitInvalidoException.class)
    public void seDetectanCuitInvalidos() {
        Empresa miEmpresa2 = new Empresa("mc Donaldo", "Arcos Dorados SRL",
                "27-123456-1", "Av. Corrientes 5600", new SectorServicios(), 150000);
    }

}

