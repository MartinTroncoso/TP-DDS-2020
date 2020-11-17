package dds.gesoc.model.organizaciones;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import dds.gesoc.model.RepoEntidades.RepoEntidades;

@Entity
@DiscriminatorValue("B")
public class EntidadBase extends Entidad {
	
    private String descripcion;
    
    @Transient
    private RepoEntidades repoEntidades;
    
    public EntidadBase() {
    }

    public EntidadBase(String nombreFicticio, Categoria categoria, double montoEsperado, String descripcion) {
        super(nombreFicticio, categoria, montoEsperado);
        setDescripcion(descripcion);
        this.repoEntidades = RepoEntidades.getInstance();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public void formarParteDeEntidadJuridica() {
        if(categoria != null)
            categoria.aplicarReglasDeNegocio(this, ReglaDeNegocio.ENT_BASE_FORMA_PARTE_ENT_JURIDICA, null, null);
            repoEntidades.agregarEntidadBaseDeEntidadJuridica(this);
    }

}
