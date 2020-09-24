package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import dds.gesoc.exceptions.DniOCuitInvalidoException;

@Entity
@DiscriminatorValue("J")
public class EntidadJuridica extends Entidad{

	private String razonSocial;
    private String cuit;
    private String direccionPostal;
    private int codigoInscripcionIGJ;
    
    @OneToMany
    @JoinColumn(name = "ent_juridica_propietaria")
    private List<Entidad> entidades = new ArrayList<>();

    @Transient
    private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}"; //2 dígitos iniciales + "-" + 8 dígitos + "-" + 1 dígito

    public EntidadJuridica() {
    }
    
    public EntidadJuridica (String nombreFicticio, Categoria categoria, double montoEsperado, String razonSocial,String cuit,String direccionPostal) {
        super(nombreFicticio, categoria, montoEsperado);
        this.razonSocial = razonSocial;

        if (!Pattern.matches(cuitValidoRegex, cuit))
            throw new DniOCuitInvalidoException("El cuit o dni no tiene formato valido");
        this.cuit = cuit;

        this.direccionPostal = direccionPostal;
    }
    
    public List<Entidad> getEntidades(){
    	return entidades;
    }

    public void setCodigoInscripcionIGJ(int codigoInscripcionIGJ) {
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }

    public void agregarEntidadBase(EntidadBase entidadNueva){
    	//BLOQUEOS CATEGORIA
	if(categoria != null)    
    		categoria.aplicarReglasDeNegocio(this, TipoRegla.ENT_JURIDICA_AGREGA_ENT_BASES, null, null);

        entidadNueva.formarParteDeEntidadJuridica();
        entidades.add(entidadNueva);
    }
    
}



