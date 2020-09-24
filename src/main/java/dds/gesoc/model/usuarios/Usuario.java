package dds.gesoc.model.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import javax.persistence.*;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.model.egresos.ResultadoValidacion;
import dds.gesoc.model.organizaciones.Entidad;

@Entity
@Table(name="usuario")
public class Usuario extends EntidadPersistente{
	
	@Column
	private String nombreUsuario;
	
	@Column
	private String contrasenia;
	
	@Enumerated
	private TipoUsuario tipoUsuario;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="id_entidad", referencedColumnName = "id")
	private Entidad entidad;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ResultadoValidacion> bandejaDeMensajes;
	
	public Usuario(){}
	
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
		bandejaDeMensajes.add(respuestaEgreso);
	}


	public int mensajesEnBandeja() {
		return bandejaDeMensajes.size();
	}
}
