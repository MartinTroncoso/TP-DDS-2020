package dds.gesoc.model.organizaciones;

public class EntidadBase extends Entidad{

    private String descripcion;

    public EntidadBase (String nombreFicticio, Categoria categoria, double montoEsperado, String descripcion) {
        super(nombreFicticio, categoria, montoEsperado);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
