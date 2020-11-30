package dds.gesoc.model.RepoEntidades;

import dds.gesoc.exceptions.EntidadBaseTieneEntidaJuridicaException;
import dds.gesoc.exceptions.UsuarioConMismoNombreEncontradoException;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;

import java.util.ArrayList;
import java.util.List;

import dds.gesoc.model.usuarios.RepoUsuarios;
import dds.gesoc.model.usuarios.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepoEntidades implements WithGlobalEntityManager{

    private static RepoEntidades repoEntidades;
    //Cada vez que se crea una entidad, se guarda acá
    private List<EntidadBase> entidadesBaseEnUnaEntidadJuridica = new ArrayList<>();
    //private List<EntidadJuridica> entidadesJuridicas = new ArrayList<>();

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
