package dds.gesoc.model.geografia;

import java.util.List;

public class JPaisConProvincias {
	private String id;
	private String name;
	private List<JProvincia> states;
	
	public JPaisConProvincias(String id, String name, List<JProvincia> states) {
		this.id = id;
		this.name = name;
		this.states = states;
	}
	
	public String getId() {
		return id;
	}
	
	public List<JProvincia> getStates() {
		return states;
	}
}
