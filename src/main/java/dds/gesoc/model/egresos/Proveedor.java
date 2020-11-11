package dds.gesoc.model.egresos;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.exceptions.DniOCuitInvalidoException;

@Entity
@Table(name = "proveedor")
public class Proveedor extends EntidadPersistente{
	
	@Column
	private String nombreORazonSocial;
	
	@Column
	private String dniOCuit;
	
	@Column
	private String pais;
	
	@Column
	private String provincia;
	
	@Column
	private String ciudad;
	
	@Column
	private String direccion;
	
	@Transient
	private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}";
	
	public Proveedor() {
		RepoProveedores.getInstance().agregarProveedor(this);
	}
	
	public Proveedor(String nombreORazonSocial) {
		this.nombreORazonSocial = nombreORazonSocial;
		RepoProveedores.getInstance().agregarProveedor(this);
	}
	
	public Proveedor(String nombreORazonSocial, String dniOCuit) {
		this.nombreORazonSocial = nombreORazonSocial;
		this.establecerDniOCuit(dniOCuit);
		RepoProveedores.getInstance().agregarProveedor(this);
	}
	
	public Proveedor(String nombreORazonSocial, String dniOCuit, DireccionPostal direccionPostal) {
		this.nombreORazonSocial = nombreORazonSocial;
		this.establecerDniOCuit(dniOCuit);
		this.setDireccionPostal(direccionPostal);
		RepoProveedores.getInstance().agregarProveedor(this);
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
		return null;
		//TODO: como conseguir la direccion postal ahora
	}

	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.pais = direccionPostal.getPais();
		this.provincia = direccionPostal.getProvincia();
		this.ciudad = direccionPostal.getCiudad();
		this.direccion = direccionPostal.getDireccion();
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}	
}
