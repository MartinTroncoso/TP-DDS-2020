package dds.gesoc.model.RepoEntidades;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;

public class RepositoriosEntidadesH implements WithGlobalEntityManager{

	private static RepositoriosEntidadesH repoEntidades;

	public static RepositoriosEntidadesH getInstance() {
		if (repoEntidades == null) {
			repoEntidades = new RepositoriosEntidadesH();
		}
		return repoEntidades;
	}
	
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
    
    public Entidad buscar(int id){
        return entityManager().find(Entidad.class, id);
    }

	public List<Entidad> listar() {
		return entityManager().createQuery("from Entidad", Entidad.class)
		.getResultList();
	}

	public void agregar(Entidad entidad) {
		entityManager().persist(entidad);
	}

	
}
