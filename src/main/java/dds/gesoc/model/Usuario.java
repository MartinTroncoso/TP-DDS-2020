package dds.gesoc.model;

public class Usuario {
	
	private String nombreUsuario;
	private String contrasenia;
	TipoUsuario tipoUsuario;
	
	public Usuario(String nombreUsuario, String contrasenia, TipoUsuario tipoUsuario) {
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.tipoUsuario = tipoUsuario;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	boolean autenticarUsuario(String contraseniaCandidata) {
		String contraseniaCandidataHasheada = CreadorHash.getInstance().hashContrasenia(contraseniaCandidata);
		return contrasenia.equals(contraseniaCandidataHasheada);
	}
	
}
