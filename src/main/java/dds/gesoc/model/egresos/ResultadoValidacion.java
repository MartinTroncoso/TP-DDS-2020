package dds.gesoc.model.egresos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacion {
    private List<String> mensajes;
    private LocalDateTime horaDeMensaje;

    public ResultadoValidacion() {
        this.mensajes = new ArrayList<>();
    }

    public void agregarMensaje(String mensaje) {
        mensajes.add(mensaje);
    }

    public void actualizarFecha() {
        this.horaDeMensaje = LocalDateTime.now();
    }
}
