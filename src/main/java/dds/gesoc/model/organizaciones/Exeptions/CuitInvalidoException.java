package dds.gesoc.model.organizaciones.Exeptions;

public class CuitInvalidoException extends RuntimeException {

    public CuitInvalidoException (String mensaje) {
        super(mensaje);
    }
}
