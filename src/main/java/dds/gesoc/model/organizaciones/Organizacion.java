package dds.gesoc.model.organizaciones;

import java.util.ArrayList;
import java.util.List;

public class Organizacion  {

    private List<Entidad> estructurasDeEntidades = new ArrayList<>();

    public void agregarEntidad(Entidad unaEntidad) {
        estructurasDeEntidades.add(unaEntidad);
    }
}
