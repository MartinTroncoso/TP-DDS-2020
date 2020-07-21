package dds.gesoc.model.egresos;

public class Etiqueta {
    private String nombre;

    public Etiqueta(String nombre) {
        this.nombre = nombre.toLowerCase();
    }

    public void renombrar(String nuevoNombre) {
        this.nombre = nuevoNombre;
    }

    public String getNombre() {
        return nombre;
    }
}
