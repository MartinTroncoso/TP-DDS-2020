package dds.gesoc.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import dds.gesoc.exceptions.DireccionPostalIncorrectaException;
import dds.gesoc.model.egresos.DireccionPostal;
import dds.gesoc.model.egresos.Proveedor;

public class ProveedoresTest {
	Proveedor unProveedor;
    private static String NOMBRE = "nombre";
    private static String DNI = "41919424";
    private static String VARIABLE_SARASA = "sarasa";
    private static String VARIABLE_PAIS = "argentina";
    private static String VARIABLE_PROV = "córdoba";
    private static String VARIABLE_CIUDAD = "alta gracia";
    
    @Before
    public void init() {
    	this.unProveedor = new Proveedor(NOMBRE, DNI);
    }
    
    @Test(expected = DireccionPostalIncorrectaException.class)
    public void proveedorConPaisNoPermitido() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_SARASA, VARIABLE_SARASA, VARIABLE_SARASA, VARIABLE_SARASA));
    }
    
    @Test(expected = DireccionPostalIncorrectaException.class)
    public void proveedorConProvinciaNoPermitida() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_PAIS, VARIABLE_SARASA, VARIABLE_SARASA, VARIABLE_SARASA));
    }
    
    @Test(expected = DireccionPostalIncorrectaException.class)
    public void proveedorConCiudadNoPermitida() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_PAIS, VARIABLE_PROV, VARIABLE_SARASA, VARIABLE_SARASA));
    }
    
    @Test
    public void proveedorConDireccionPostalPermitida() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_PAIS, VARIABLE_PROV, VARIABLE_CIUDAD, VARIABLE_SARASA));
    	Assert.assertEquals("argentina", unProveedor.getDireccionPostal().getPais());
    	Assert.assertEquals("córdoba", unProveedor.getDireccionPostal().getProvincia());
    	Assert.assertEquals("alta gracia", unProveedor.getDireccionPostal().getCiudad());
    	Assert.assertEquals("sarasa", unProveedor.getDireccionPostal().getDireccion());
    }
    
    /*
     * Tests que fallan para comprobar si sale su respectivo mensaje de excepcion
     */
    
    @Test
    public void proveedorConPaisNoPermitidoFalla() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_SARASA, VARIABLE_SARASA, VARIABLE_SARASA, VARIABLE_SARASA));
    }
    
    @Test
    public void proveedorConProvinciaNoPermitidaFalla() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_PAIS, VARIABLE_SARASA, VARIABLE_SARASA, VARIABLE_SARASA));
    }
    
    @Test
    public void proveedorConCiudadNoPermitidaFalla() {
    	unProveedor.setDireccionPostal(
    			new DireccionPostal(VARIABLE_PAIS, VARIABLE_PROV, VARIABLE_SARASA, VARIABLE_SARASA));
    }
}
