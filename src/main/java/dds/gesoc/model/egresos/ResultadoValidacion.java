package dds.gesoc.model.egresos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import converters.LocalDateTimeAttributeConverter;
import dds.gesoc.entities.EntidadPersistente;

@Entity
public class ResultadoValidacion extends EntidadPersistente{
	
	@Column
	private String asunto;
	
	@ElementCollection
    private List<String> mensajes;
	
	@Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime horaDeMensaje;

    public ResultadoValidacion() {
        this.mensajes = new ArrayList<>();
    }

    public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public void agregarMensaje(String mensaje) {
        mensajes.add(mensaje);
    }

    public void actualizarFecha() {
        this.horaDeMensaje = LocalDateTime.now();
    }

	public LocalDateTime getHoraDeMensaje() {
		return horaDeMensaje;
	}

	public void setHoraDeMensaje(LocalDateTime horaDeMensaje) {
		this.horaDeMensaje = horaDeMensaje;
	}

	public List<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}
}
