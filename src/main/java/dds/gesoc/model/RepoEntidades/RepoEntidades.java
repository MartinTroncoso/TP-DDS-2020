package dds.gesoc.model.RepoEntidades;

import dds.gesoc.exceptions.EntidadBaseTieneEntidaJuridicaException;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;

import java.util.ArrayList;
import java.util.List;

public class RepoEntidades {

    private static RepoEntidades repoEntidades;
    //Cada vez que se crea una entidad, se guarda acá
    private List<EntidadBase> entidadesBaseEnUnaEntidadJuridica = new ArrayList<>();
    //private List<EntidadJuridica> entidadesJuridicas = new ArrayList<>();

    //private RepoEntidades();

    public static RepoEntidades getInstance() {
        if (repoEntidades == null) {
            repoEntidades = new RepoEntidades();
        }
        return repoEntidades;
    }

    public void agregarEntidadBaseDeEntidadJuridica(EntidadBase unaEntidad) {
        if(entidadBaseTieneEntidadJuridica(unaEntidad))
            throw new EntidadBaseTieneEntidaJuridicaException("La entidad base ya está asociada a una entidad jurídica");

        entidadesBaseEnUnaEntidadJuridica.add(unaEntidad);
    }

    public boolean entidadBaseTieneEntidadJuridica(EntidadBase unaEntidad) {
        return entidadesBaseEnUnaEntidadJuridica.stream()
                .anyMatch(entidad -> entidad.getNombreFicticio().equalsIgnoreCase(unaEntidad.getNombreFicticio()));
    }


}
