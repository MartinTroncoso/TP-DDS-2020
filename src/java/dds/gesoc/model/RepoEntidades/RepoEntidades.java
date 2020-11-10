package dds.gesoc.model.RepoEntidades;

import dds.gesoc.exceptions.EntidadBaseTieneEntidaJuridicaException;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepoEntidades implements WithGlobalEntityManager{

    private static RepoEntidades repoEntidades;
    //Cada vez que se crea una entidad, se guarda acá
    private List<EntidadBase> entidadesBaseEnUnaEntidadJuridica = new ArrayList<>();
    //private List<EntidadJuridica> entidadesJuridicas = new ArrayList<>();
    private List<Entidad> todasLasEntidades = new ArrayList<>();

    private RepoEntidades() {};

    public static RepoEntidades getInstance() {
        if (repoEntidades == null) {
            repoEntidades = new RepoEntidades();
        }
        return repoEntidades;
    }

    public void agregarEntidadBaseDeEntidadJuridica(EntidadBase entidadBase) {
        if(entidadBaseTieneEntidadJuridica(entidadBase))
            throw new EntidadBaseTieneEntidaJuridicaException("La entidad base ya está asociada a una entidad jurídica");

        entidadesBaseEnUnaEntidadJuridica.add(entidadBase);
    }

    public boolean entidadBaseTieneEntidadJuridica(EntidadBase unaEntidad) {
        return entidadesBaseEnUnaEntidadJuridica.stream()
                .anyMatch(entidad -> entidad.getNombreFicticio().equalsIgnoreCase(unaEntidad.getNombreFicticio()));
    }
    
    public List<Entidad> getEntidades(){
    	return todasLasEntidades;
    }
    
    public Entidad buscar(long id){
        return entityManager().find(Entidad.class, id);
    }
}