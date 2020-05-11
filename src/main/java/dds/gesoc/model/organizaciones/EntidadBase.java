package dds.gesoc.model.organizaciones;

public class EntidadBase extends Entidad {

    private String descripcion;
    //TODO Es una lista de egresos. Preguntar si es de la entidad o de la organizacion
    private EntidadJuridica entJuridicaPropietaria;

    public EntidadBase (String nombreFicticio, String descripcion) {
        super(nombreFicticio);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EntidadJuridica getEntJuridicaPropietaria() {
        return entJuridicaPropietaria;
    }

    public void setEntJuridicaPropietaria(EntidadJuridica entJuridicaPropietaria) {
        this.entJuridicaPropietaria = entJuridicaPropietaria;
    }
}
