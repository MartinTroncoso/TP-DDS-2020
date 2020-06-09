package dds.gesoc.model.egresos;

public class Documento {
	/*
	 * como para esta entrega no es necesariamente esperado 
	 * que los egresos tengan asociado un documento comercial, 
	 * de momento el tipo lo pondremos como un String
	 */
	private String tipo;
	private int numero;
	
	public Documento(String tipo, int numero) {
		this.tipo = tipo;
		this.numero = numero;
	}
	
	
}
