package dds.gesoc.model.egresos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.gesoc.exceptions.UsuarioRevisorException;
import dds.gesoc.model.usuarios.Usuario;

public class Egreso {
	private DatosEgreso datosEgreso;
	private LocalDate fechaOperacion;
	private List<Item> items;
	private List<Presupuesto> presupuestos;
	private List<Presupuesto> presupuestosValidados;
	private int cantPresupuestosMinima;
	private CriterioSeleccionProveedor criterioProveedor;
	private List<Usuario> usuariosRevisores;
	
	public Egreso(DatosEgreso datosEgreso, int cantPresupuestosMinima, CriterioSeleccionProveedor criterioProveedor) {
		this.datosEgreso = datosEgreso;
		this.fechaOperacion = LocalDate.now();
		this.items = new ArrayList<Item>();
		this.presupuestos = new ArrayList<>();
		this.presupuestosValidados = new ArrayList<>();
		this.cantPresupuestosMinima = cantPresupuestosMinima;
		this.criterioProveedor = criterioProveedor;
		this.usuariosRevisores = new ArrayList<>();
	}
	
	public void agregarItem(Item item) {
		this.items.add(item);
	}
	
	public List<Item> getItems(){
		return this.items;
	}
	
	public DatosEgreso getDatosEgreso() {
		return this.datosEgreso;
	}

	public LocalDate getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(LocalDate fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	
	public void agregarPresupuesto(Presupuesto presupuesto){
		this.getPresupuestos().add(presupuesto);
	}

	public List<Presupuesto> getPresupuestos() {
		return this.presupuestos;
	}

	public List<Presupuesto> getPresupuestosValidados() {
		return this.presupuestosValidados;
	}

	public int getCantPresupuestosMinima() {
		return this.cantPresupuestosMinima;
	}

	public void setCantPresupuestosMinima(int cantPresupuestosMinima) {
		this.cantPresupuestosMinima = cantPresupuestosMinima;
	}

	public CriterioSeleccionProveedor getCriterioProveedor() {
		return this.criterioProveedor;
	}

	public void setCriterioProveedor(CriterioSeleccionProveedor criterioProveedor) {
		this.criterioProveedor = criterioProveedor;
	}

	public List<Usuario> getUsuariosRevisores() {
		return this.usuariosRevisores;
	}
	
	public double valorTotal() {
		return this.getItems().stream().mapToDouble(Item::getValor).sum();
	}

	public void agregarUsuarioRevisor(Usuario usuario){
		if(this.getUsuariosRevisores().contains(usuario)) {
			throw new UsuarioRevisorException("El usuario ya esta suscrito al egreso");
		}
		
		this.usuariosRevisores.add(usuario);
	}
	
	public void eliminarUsuarioRevisor(Usuario usuario){
		if(!this.getUsuariosRevisores().contains(usuario)) {
			throw new UsuarioRevisorException("El usuario no estaba suscrito al egreso en un principio");
		}
		
		this.usuariosRevisores.add(usuario);
	}
}
