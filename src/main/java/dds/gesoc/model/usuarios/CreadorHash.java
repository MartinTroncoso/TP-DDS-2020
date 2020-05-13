package dds.gesoc.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dds.gesoc.exceptions.MetodoHashIncorrectoException;

public class CreadorHash {

	private static final CreadorHash INSTANCE = new CreadorHash();
	private MessageDigest md;
	private String salt;
	
	private CreadorHash() {
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new MetodoHashIncorrectoException("Se ha definido un metodo incorrecto para encriptar la contraseña");
		}
		salt = "@DDS!#08";
	}
	
	public static CreadorHash getInstance() {
		return INSTANCE;
	}
	
	public String hashContrasenia(String posibleContrasenia) {
		String cadenaAHashear = posibleContrasenia + salt;
		return new String(md.digest(cadenaAHashear.getBytes(StandardCharsets.UTF_8)));
	}
	
}
