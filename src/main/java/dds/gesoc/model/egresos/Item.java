package dds.gesoc.model.egresos;

public class Item {
	private String descripcion;
	private double valorItem;
	
	public Item(String descripcion, double valor) {
		this.descripcion = descripcion;
		this.valorItem = valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public double getValor() {
		return this.valorItem;
	}
	
	//TODO: Ver si usar BigDecimal para el precio
}
