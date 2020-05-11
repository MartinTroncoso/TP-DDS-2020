package dds.gesoc.model.organizaciones;

public abstract class Entidad {

    protected String nombreFicticio;
    //TODO lista de egresos


    //ENUNCIADO: A lo largo del sistema, se considerará que todos los requerimientos pueden ser asociados a una
    //entidad jurídica o una entidad base, según  el usuario seleccione en cada momento

    //Supongo que se podrá hacer contabilidad de gestión para cualquier entidad
    //Además así una organización puede tener entidades de cualquier tipo en su lista
    //Lo dejo para el futuro...

    protected Entidad (String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }
}
