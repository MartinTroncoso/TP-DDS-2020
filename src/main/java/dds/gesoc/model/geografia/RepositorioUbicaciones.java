package dds.gesoc.model.geografia;

import java.util.List;

public class RepositorioUbicaciones {

	private static RepositorioUbicaciones INSTANCE = new RepositorioUbicaciones();
	
	private List<Pais> paises;
	
	private RepositorioUbicaciones() {
	}
	
	public static RepositorioUbicaciones getInstance() {
		return INSTANCE;
	}
	
	
}
