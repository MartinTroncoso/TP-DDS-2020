package dds.gesoc.model.egresos;

import java.util.regex.Pattern;

import dds.gesoc.exceptions.DniOCuitInvalidoException;

public class Proveedor {
	private String nombreORazonSocial;
	private String dniOCuit;
	private DireccionPostal direccionPostal;
	
	private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}";
	
	public Proveedor(String nombreORazonSocial) {
		this.nombreORazonSocial = nombreORazonSocial;
	}
	
	public Proveedor(String nombreORazonSocial, String dniOCuit, DireccionPostal direccionPostal) {
		this.nombreORazonSocial = nombreORazonSocial;
		this.establecerDniOCuit(dniOCuit);
		this.direccionPostal = direccionPostal;
	}

	public void establecerDniOCuit(String dniOCuit){
		if(!(dniEstaEntre(dniOCuit) || Pattern.matches(cuitValidoRegex, dniOCuit))) {
			throw new DniOCuitInvalidoException("El numero de CUIT o DNI es incorrecto");
		}
		
		this.setDniOCuit(dniOCuit);
	}

	private boolean dniEstaEntre(String dniOCuit) {
		if(!dniOCuit.contains("-")) {
			int dni = Integer.parseInt(dniOCuit);
			return dni <= 99999999 && dni > 0;
		}
		
		return false;
	}

	public String getDniOCuit() {
		return dniOCuit;
	}

	public void setDniOCuit(String dniOCuit) {
		this.dniOCuit = dniOCuit;
	}

	public String getNombreORazonSocial() {
		return nombreORazonSocial;
	}

	public void setNombreORazonSocial(String nombreORazonSocial) {
		this.nombreORazonSocial = nombreORazonSocial;
	}

	public DireccionPostal getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}
	
	
}
