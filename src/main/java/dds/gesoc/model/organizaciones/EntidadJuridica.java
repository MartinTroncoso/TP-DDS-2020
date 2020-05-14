package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import dds.gesoc.exceptions.CuitInvalidoException;
import dds.gesoc.model.RepoEntidades.RepoEntidades;

public class EntidadJuridica extends Entidad
{
    private String razonSocial;
    //TODO
    //Debe cumplir con una expresión regulare
    private String cuit;
    private String direccionPostal;
    private int codigoInscripcionIGJ;
    private List<EntidadBase> entidadesBase = new ArrayList<>();
    private RepoEntidades repoEntidades;

    private String cuitValidoRegex = "\\d{2}-\\d{8}-\\d{1}"; //2 dígitos iniciales + "-" + 8 dígitos + "-" + 1 dígito

    public EntidadJuridica (String nombreFicticio, String razonSocial,String cuit,String direccionPostal) {
        super(nombreFicticio);
        this.razonSocial = razonSocial;

        if (!Pattern.matches(cuitValidoRegex, cuit))
            throw new CuitInvalidoException("El cuit no tiene formato válido");
        this.cuit = cuit;

        this.direccionPostal = direccionPostal;
        this.repoEntidades = RepoEntidades.getInstance();
    }

    public void setCodigoInscripcionIGJ(int codigoInscripcionIGJ) {
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }

    public void agregarEntidadBase(EntidadBase entidadBaseNueva) {
        repoEntidades.agregarEntidadBaseDeEntidadJuridica(entidadBaseNueva);
        entidadesBase.add(entidadBaseNueva);
    }
}
