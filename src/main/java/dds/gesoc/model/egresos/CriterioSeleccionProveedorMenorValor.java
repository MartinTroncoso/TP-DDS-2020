package dds.gesoc.model.egresos;

import java.util.List;

public class CriterioSeleccionProveedorMenorValor implements CriterioSeleccionProveedor {

    @Override
    public Proveedor seleccionarProveedor(List<Presupuesto> listaDePresupuestos) {
        return listaDePresupuestos.stream().findFirst(p);
    }
}
