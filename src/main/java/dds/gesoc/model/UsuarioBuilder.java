package dds.gesoc.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dds.gesoc.exceptions.NombreUsuarioNullException;
import dds.gesoc.exceptions.PeoresContraseniasException;
import dds.gesoc.exceptions.UsuarioIncompletoException;

public class UsuarioBuilder {

	private String nombreUsuario;
	private String contrasenia;
	TipoUsuario tipoUsuario;

	public UsuarioBuilder() {
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public String getContrasenia() {
		return this.contrasenia;
	}
	
	public void especificarNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public void especificarTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public void especificarContrasenia(String posibleContrasenia) {
		if (nombreUsuario == null) {
			throw new NombreUsuarioNullException(
					"Se debe especificar el nombre de usuario antes que la contraseña, para poder hacer las validaciones");
		}
		controlarTopPeoresContrasenias(posibleContrasenia);
		validacionComplejidadContrasenia(posibleContrasenia);
		this.contrasenia = CreadorHash.getInstance().hashContrasenia(posibleContrasenia);
	}
	
	public Usuario crearUsuario() {
		if(nombreUsuario == null || contrasenia == null || tipoUsuario == null) {
			throw new UsuarioIncompletoException("No se ha especificado todos los datos del usuario");
		}
		return new Usuario(nombreUsuario, contrasenia, tipoUsuario);
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
