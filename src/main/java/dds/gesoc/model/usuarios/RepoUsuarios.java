package dds.gesoc.model.usuarios;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dds.gesoc.exceptions.UsuarioConMismoNombreEncontradoException;



public class RepoUsuarios implements WithGlobalEntityManager{

	private static RepoUsuarios repoUsuarios;
	
	public static RepoUsuarios getInstance() {
		if (repoUsuarios == null) {
			repoUsuarios = new RepoUsuarios();
		}
		return repoUsuarios;
	}
	
	public List<Usuario> todosLosUsuarios() {
		return entityManager()
				.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}
	
	public void agregarUsuario(Usuario unUsuario) {
		Usuario usuarioConMismoNombre = RepoUsuarios.getInstance().buscarUsuarioPorNombre(unUsuario.getNombreUsuario());
		if(usuarioConMismoNombre != null) {
			throw new UsuarioConMismoNombreEncontradoException("Ya hay un usuario con nombre " + unUsuario.getNombreUsuario());
		}
		entityManager().persist(unUsuario);
	}
	
	public Usuario buscarUsuarioPorId(int id) {
		return entityManager().find(Usuario.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Usuario buscarUsuarioPorNombre(String nombre) {
		List<Usuario> usuariosEncontrados = entityManager()
				.createQuery("from Usuario where nombreUsuario = :nombre")
				.setParameter("nombre", nombre)
				.getResultList();
		//TODO: Lanzar excepcion si usuariosEncontrados.size() > 1
			
		return usuariosEncontrados.size() == 1? usuariosEncontrados.get(0) : null;
	}
}
