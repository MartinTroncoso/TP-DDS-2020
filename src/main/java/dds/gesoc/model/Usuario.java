package dds.gesoc.model;

public class Usuario {
	
	private String nombreUsuario;
	private String contrasenia;
	TipoUsuario tipoUsuario;
	Entidad entidad;
	
	public Usuario(String nombreUsuario, String contrasenia, TipoUsuario tipoUsuario, Entidad entidad) {
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.tipoUsuario = tipoUsuario;
		this.entidad = entidad;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	boolean autenticarUsuario(String contraseniaCandidata) {
		String contraseniaCandidataHasheada = CreadorHash.getInstance().hashContrasenia(contraseniaCandidata);
		return contrasenia.equals(contraseniaCandidataHasheada);
	}
	
}
