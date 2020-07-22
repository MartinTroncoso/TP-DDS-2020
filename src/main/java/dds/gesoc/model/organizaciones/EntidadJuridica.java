package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dds.gesoc.exceptions.DniOCuitInvalidoException;
import dds.gesoc.model.RepoEntidades.RepoEntidades;

public class EntidadJuridica extends Entidad{

	private String razonSocial;
    private String cuit;
    private String direccionPostal;
    private int codigoInscripcionIGJ;
    private List<Entidad> entidades = new ArrayList<>();
    private Categoria categoria;
    private double montoEsperado;
    private RepoEntidades repoEntidades;

    private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}"; //2 dígitos iniciales + "-" + 8 dígitos + "-" + 1 dígito

    public EntidadJuridica (String nombreFicticio, Categoria categoria, double montoEsperado, String razonSocial,String cuit,String direccionPostal) {
        super(nombreFicticio, categoria, montoEsperado);
        this.razonSocial = razonSocial;

        if (!Pattern.matches(cuitValidoRegex, cuit))
            throw new DniOCuitInvalidoException("El cuit no tiene formato valido");
        this.cuit = cuit;

        this.direccionPostal = direccionPostal;
        this.repoEntidades = RepoEntidades.getInstance();
    }
    
    public List<Entidad> getEntidades(){
    	return entidades;
    }

    public void setCodigoInscripcionIGJ(int codigoInscripcionIGJ) {
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }

    public void agregarEntidadBase(EntidadBase entidadNueva){
    	//BLOQUEOS CATEGORIA
	categoria.aplicarReglasDeNegocio(this);
        repoEntidades.agregarEntidadBaseDeEntidadJuridica(entidadNueva);
        entidades.add(entidadNueva);
    }
    
}
