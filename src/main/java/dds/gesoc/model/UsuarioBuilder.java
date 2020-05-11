package dds.gesoc.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dds.gesoc.exceptions.AtributoNullException;
import dds.gesoc.exceptions.NombreUsuarioNullException;
import dds.gesoc.exceptions.PeoresContraseniasException;
import dds.gesoc.exceptions.UsuarioIncompletoException;
import dds.gesoc.model.organizaciones.Entidad;

public class UsuarioBuilder {

	private String nombreUsuario;
	private String contrasenia;
	private TipoUsuario tipoUsuario;
	private Entidad entidad;

	public UsuarioBuilder() {
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public String getContrasenia() {
		return this.contrasenia;
	}
	
	public Entidad getEntidad() {
		return this.entidad;
	}
	
	public void especificarNombreUsuario(String nombreUsuario) {
		validateNonNull(nombreUsuario);
		this.nombreUsuario = nombreUsuario;
	}
	
	public void especificarTipoUsuario(TipoUsuario tipoUsuario) {
		validateNonNull(tipoUsuario);
		this.tipoUsuario = tipoUsuario;
	}
	
	public void especificarEntidad(Entidad entidad) {
		validateNonNull(entidad);
		this.entidad = entidad;
	}

	public void especificarContrasenia(String posibleContrasenia) {
		validateNonNull(entidad);
		if (nombreUsuario == null) {
			throw new NombreUsuarioNullException(
					"Se debe especificar el nombre de usuario antes que la contraseña, para poder hacer las validaciones");
		}
		controlarTopPeoresContrasenias(posibleContrasenia);
		validacionComplejidadContrasenia(posibleContrasenia);
		this.contrasenia = CreadorHash.getInstance().hashContrasenia(posibleContrasenia);
	}
	
	public Usuario crearUsuario() {
		validateNonNull(nombreUsuario);
		validateNonNull(tipoUsuario);
		validateNonNull(entidad);
		validateNonNull(entidad);
		return new Usuario(nombreUsuario, contrasenia, tipoUsuario, entidad);
		//TODO: falta la Entidad en ese constructor
	}

	public void controlarTopPeoresContrasenias(String contrasenia) {
		List<String> contraseniasDebiles = obtenerContrasenias();
		if (contraseniasDebiles.contains(contrasenia)) {
			throw new PeoresContraseniasException("La contraseña es una de las 10.000 peores contraseñas");
		}
	}

	private void validacionComplejidadContrasenia(String contrasenia) {
		List<ValidacionContrasenia> validaciones = Arrays.asList(ValidacionContrasenia.values());
		validaciones.forEach(v -> v.ejecutar(this.getNombreUsuario(), contrasenia));
	}
	
	private void validateNonNull(Object obj) {
		if(obj == null) {
			throw new AtributoNullException("Se trato de especificar un null como atributo de usuario");
		}
	}

	private List<String> obtenerContrasenias() {
		InputStream resource = this.getClass().getClassLoader().getResourceAsStream("10k-most-common.txt");
	    List<String> contraseniasDebiles = new BufferedReader(
		        new InputStreamReader(
		            resource,
		            StandardCharsets.UTF_8
		        )
		    ).lines()
		    .collect(Collectors.toList());
		return contraseniasDebiles;
	}
}
