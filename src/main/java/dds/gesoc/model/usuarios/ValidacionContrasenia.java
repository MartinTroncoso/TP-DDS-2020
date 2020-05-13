package dds.gesoc.model.usuarios;

import java.util.Collections;
import java.util.stream.Stream;

import dds.gesoc.exceptions.ContraseniaConLongitudCortaException;
import dds.gesoc.exceptions.ContraseniaConNombreUsuarioException;
import dds.gesoc.exceptions.ContraseniaConRepetidosSeguidosException;

public enum ValidacionContrasenia {
	
	LONGITUD_MINIMA {
		@Override
		public void ejecutar(String nombre, String contrasenia) {
			if(contrasenia.length() < 8)
				throw new ContraseniaConLongitudCortaException("Contraseña ingresada: " + contrasenia + ", debe ser de minimo de 8 caracteres");
		}
	},
	SIN_NOMBRE_USUARIO {
		@Override
		public void ejecutar(String nombre, String contrasenia) {
			if(contrasenia.toLowerCase().contains(nombre.toLowerCase())) {
				throw new ContraseniaConNombreUsuarioException(contrasenia + " contiene el nombre de usuario ingresado");
			}
		}
	},
	SIN_CARACTERES_REPETIDOS_SEGUIDOS {
		@Override
		public void ejecutar(String nombre, String contrasenia) {
			Stream<Character> sch = contrasenia.chars().mapToObj(i -> (char) i);
			sch.forEach(c ->{
				String caracRepetido = String.join("", Collections.nCopies(4, c.toString()));
				if(contrasenia.contains(caracRepetido)) {
					throw new ContraseniaConRepetidosSeguidosException("La contraseña ingresada contiene el caracter " + c.toString() + " repetido de forma consecutiva");
				}
			});
			
		}
	};
	public abstract void ejecutar(String nombre, String contrasenia);
}
