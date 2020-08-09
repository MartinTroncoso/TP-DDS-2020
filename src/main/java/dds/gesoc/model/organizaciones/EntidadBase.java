package dds.gesoc.model.organizaciones;

import dds.gesoc.model.RepoEntidades.RepoEntidades;

public class EntidadBase extends Entidad {

    private String descripcion;
    private RepoEntidades repoEntidades;

    public EntidadBase(String nombreFicticio, Categoria categoria, double montoEsperado, String descripcion) {
        super(nombreFicticio, categoria, montoEsperado);
        this.descripcion = descripcion;
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
            categoria.aplicarReglasDeNegocio(this, TipoRegla.ENT_BASE_FORMA_PARTE_ENT_JURIDICA, null, null);
        repoEntidades.agregarEntidadBaseDeEntidadJuridica(this);
    }

}
