package dds.gesoc.model.egresos;

import java.time.LocalDateTime;

public class ResultadoValidacion {
    private String mensaje;
    LocalDateTime horaDeMensaje;

    public ResultadoValidacion(String mensaje) {
        this.mensaje = mensaje;
        this.horaDeMensaje = LocalDateTime.now();
    }

}
