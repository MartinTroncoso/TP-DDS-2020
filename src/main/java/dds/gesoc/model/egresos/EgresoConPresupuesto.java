package dds.gesoc.model.egresos;

import java.util.ArrayList;

public class EgresoConPresupuesto extends Egreso{

    List<Presupuesto> presupuestosPropuestos = new ArrayList<>();


    public EgresoConPresupuesto(DatosEgreso datosDelEgreso, int cantMinimaPresupuestos) {
        //TODO Egreso con presupuesto necesita recibir un parámetro extra en el constructor
        //TODO La clase Egreso debe recibir menos parámetros y estos deben ser agrupados en una misma estructura, para que EgresoConPresupuesto no tenda dos constructores
        super(datosDelEgreso.getProveedor(), datosDelEgreso.getDocComercial() ,datosDelEgreso.getMedioPago());
    }



}
