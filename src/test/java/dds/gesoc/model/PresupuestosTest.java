package dds.gesoc.model;

import dds.gesoc.exceptions.PresupuestoSinEgresoAsociadoException;
import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Presupuesto;
import dds.gesoc.model.egresos.Proveedor;
import org.junit.Before;
import org.junit.Test;

public class PresupuestosTest {

    private Presupuesto presupuestoJardin;
    private Proveedor unProveedor;
    private Documento presupuestoDocumento;


    @Before
    public void init() {
        unProveedor = new Proveedor("Casa 9 de Julio");
        presupuestoDocumento = new Documento("Presupuesto", 44);
    }


    //2. Cada ítem de un presupuesto debe estar asociado obligatoriamente a un ​egreso​
    @Test (expected = PresupuestoSinEgresoAsociadoException.class)
    public void noSePuedenCrearPresupuestosSinEgresoAsociado() {
        new Presupuesto(unProveedor, presupuestoDocumento, "Oferta muebles de jardin", null);
    }

    //Si la compra requiere presupuestos se debe verificar que
    //efectivamente se encuentran cargada la cantidad indicada


    // Si la compra requiere presupuestos se debe verificar que la compra haya sido realizada en
    //base a alguno de esos presupuestos


    // debe validarse que, entre
    //los presupuestos cargados en el sistema, se haya seleccionado el de menor valor.
}
