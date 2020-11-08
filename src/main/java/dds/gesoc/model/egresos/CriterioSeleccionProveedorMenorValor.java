package dds.gesoc.model.egresos;

import java.util.Comparator;
import java.util.List;

public class CriterioSeleccionProveedorMenorValor implements CriterioSeleccionProveedor {


    // TODO Acá supngo que todos los presupuestos tienen el ValorTotal en la misma moneda y comparo directamente el valor numérico, no el ValorMonetario
    //TODO corregir si es necesario
    @Override
    public Proveedor seleccionarProveedor(List<Presupuesto> listaDePresupuestos) {
        return listaDePresupuestos.stream().min(Comparator.comparing(Presupuesto::valorTotalNumerico)).get().getUnProveedor();
    }
}
