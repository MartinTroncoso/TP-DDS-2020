package dds.gesoc.model.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import dds.gesoc.model.egresos.ResultadoValidacion;
import dds.gesoc.model.organizaciones.Entidad;

public class Usuario {
	
	private String nombreUsuario;
	private String contrasenia;
	private TipoUsuario tipoUsuario;
	private Entidad entidad;
	private Stack<ResultadoValidacion> bandejaDeMensajes = new Stack<>();
	
	public Usuario(String nombreUsuario, String contrasenia, TipoUsuario tipoUsuario, Entidad entidad) {
		
		this.nombreUsuario = Objects.requireNonNull(nombreUsuario);
		this.tipoUsuario = Objects.requireNonNull(tipoUsuario);
		this.entidad = Objects.requireNonNull(entidad);
		
		String posibleContrasenia = Objects.requireNonNull(contrasenia);
		ValidadorDeContrasenias.getInstance().validarContrasenia(nombreUsuario, posibleContrasenia);
		this.contrasenia = CreadorHash.getInstance().hashContrasenia(posibleContrasenia);
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public boolean autenticarUsuario(String contraseniaCandidata) {
		String contraseniaCandidataHasheada = CreadorHash.getInstance().hashContrasenia(contraseniaCandidata);
		return contrasenia.equals(contraseniaCandidataHasheada);
	}


	public void serNotificado(ResultadoValidacion respuestaEgreso) {
		bandejaDeMensajes.push(respuestaEgreso);
	}
}
