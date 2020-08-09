package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dds.gesoc.exceptions.DniOCuitInvalidoException;

public class EntidadJuridica extends Entidad{

	private String razonSocial;
    private String cuit;
    private String direccionPostal;
    private int codigoInscripcionIGJ;
    private List<Entidad> entidades = new ArrayList<>();

    private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}"; //2 dígitos iniciales + "-" + 8 dígitos + "-" + 1 dígito

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



