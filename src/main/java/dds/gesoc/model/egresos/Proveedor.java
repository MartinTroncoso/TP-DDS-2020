package dds.gesoc.model.egresos;

import java.util.Objects;
import java.util.regex.Pattern;

import dds.gesoc.model.egresos.exceptions.CuitIncorrectoException;
import dds.gesoc.model.egresos.exceptions.DniIncorrectoException;

public class Proveedor {
	private String nombreORazonSocial;
	private int dni;
	private String cuit;
	private String direccionPostal;
	
	private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}";
	
	public Proveedor(String nombreORazonSocial) {
		this.nombreORazonSocial = nombreORazonSocial;
	}
	
	public Proveedor(String nombreORazonSocial, int dni, String direccionPostal) {
		this.nombreORazonSocial = nombreORazonSocial;
		this.establecerDNI(dni);
		this.direccionPostal = direccionPostal;
	}
	
	public Proveedor(String nombreORazonSocial, String cuil_cuit, String direccionPostal) {
		this.nombreORazonSocial = nombreORazonSocial;
		this.establecerCuit(cuit);
		this.direccionPostal = direccionPostal;
	}
	
	public void establecerDNI(int dni) {
		if(dni <= 99999999 && dni > 0) {
			this.setDni(dni);
		}else {
			throw new DniIncorrectoException("Se ingresó un DNI negativo o de 9 cifras o mas");
		}
	}

	public void establecerCuit(String cuit){
		if(Pattern.matches(cuitValidoRegex, cuit)) {
			this.setCuit(cuit);
		}else {
			throw new CuitIncorrectoException("Se ingresó un numero de CUIT incorrecto");
		}
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombreORazonSocial() {
		return nombreORazonSocial;
	}

	public void setNombreORazonSocial(String nombreORazonSocial) {
		this.nombreORazonSocial = nombreORazonSocial;
	}

	public String getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}
	
	
}
