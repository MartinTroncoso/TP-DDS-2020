package dds.gesoc.model.egresos;

import java.util.Comparator;
import java.util.List;

/*public interface CriterioSeleccionProveedor {
	public Proveedor seleccionarProveedor(List<Presupuesto> listaDePresupuestos);
}
*/

public enum CriterioSeleccionProveedor {
	MENOR_VALOR {
		@Override
		public Proveedor seleccionarProveedor(List<Presupuesto> listaDePresupuestos) {
			return listaDePresupuestos.stream().min(Comparator.comparing(Presupuesto::getMontoTotal)).get().getProveedor();
		}
	};
	public abstract Proveedor seleccionarProveedor(List<Presupuesto> listaDePresupuestos);
}