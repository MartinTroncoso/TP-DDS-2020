package dds.gesoc.model.egresos;

import java.util.List;

public interface CriterioSeleccionProveedor {
	public Proveedor seleccionarProveedor(List<Presupuesto> listaDePresupuestos);
}
