package dds.gesoc.model.egresos;

import java.time.LocalDate;
import java.util.ArrayList;

public class DatosEgreso {
    private Proveedor proveedor;
    private Documento docComercial;
    private MedioPago medioPago;

    public DatosEgreso(Proveedor prov, Documento documento, MedioPago medioPago) {
        this.proveedor = prov;
        this.docComercial = documento;
        this.medioPago = medioPago;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Documento getDocComercial() {
        return docComercial;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }
}
