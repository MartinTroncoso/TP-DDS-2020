package dds.gesoc.model.usuarios;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import dds.gesoc.exceptions.PeoresContraseniasException;

public class ValidadorDeContrasenias {
	
	private List<ValidacionContrasenia> validaciones;
	private static ValidadorDeContrasenias INSTANCE = new ValidadorDeContrasenias();
	
	private ValidadorDeContrasenias() {	
		this.validaciones = Arrays.asList(ValidacionContrasenia.values());
	}
	
	public static ValidadorDeContrasenias getInstance() {
		return INSTANCE;
	}
	
	public void validarContrasenia(String usuario, String posibleContrasenia) {
		controlarTopPeoresContrasenias(posibleContrasenia);
		validacionComplejidadContrasenia(usuario, posibleContrasenia);
	}
	
	public void controlarTopPeoresContrasenias(String contrasenia) {
		List<String> contraseniasDebiles = obtenerContrasenias();
		if (contraseniasDebiles.contains(contrasenia)) {
			throw new PeoresContraseniasException("La contraseña es una de las 10.000 peores contraseñas");
		}
	}

	public void validacionComplejidadContrasenia(String usuario, String contrasenia) {
		validaciones.forEach(v -> v.ejecutar(usuario, contrasenia));
	}
	
	private List<String> obtenerContrasenias() {
		InputStream resource = this.getClass().getClassLoader().getResourceAsStream("10k-most-common.txt");
		Objects.requireNonNull(resource);
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
