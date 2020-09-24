package dds.gesoc.model.RepoEntidades;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;

public class RepositoriosEntidadesH implements WithGlobalEntityManager{

	public List<Entidad> todasLasEntidadesJuridicas(){
		return entityManager()
				.createQuery("from EntidadJuridica", Entidad.class)
				.getResultList();
	}
	
	public List<Entidad> todasLasEntidadesBase(){
		return entityManager()
				.createQuery("from EntidadBase", Entidad.class)
				.getResultList();
	}
	
	public List<Entidad> entidadesBaseEnUnaEntidadJuridica(){
		return entityManager()
				.createQuery("from EntidadBase where entidad_juridica_propietaria is not null", Entidad.class)
				.getResultList();
	}
	
    public boolean entidadBaseTieneEntidadJuridica(EntidadBase unaEntidad) {
        return this.entidadesBaseEnUnaEntidadJuridica().stream()
                .anyMatch(entidad -> entidad.getNombreFicticio().equalsIgnoreCase(unaEntidad.getNombreFicticio()));
    }
}
