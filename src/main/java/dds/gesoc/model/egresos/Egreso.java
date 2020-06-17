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

	private int cantPresupuestosMinima;
	private CriterioSeleccionProveedor criterioProveedor;
	private List<Usuario> usuariosRevisores;
	private RepoEgresos repoEgresos;
	private ResultadoValidacion resultadoValidacion;
	
	public Egreso(DatosEgreso datosEgreso, int cantPresupuestosMinima, CriterioSeleccionProveedor criterioProveedor) {
		this.datosEgreso = datosEgreso;
		this.fechaOperacion = LocalDate.now();
		this.items = new ArrayList<Item>();
		this.presupuestos = new ArrayList<>();

		this.cantPresupuestosMinima = cantPresupuestosMinima;
		this.criterioProveedor = criterioProveedor;
		this.usuariosRevisores = new ArrayList<>();
		this.repoEgresos = RepoEgresos.getInstance();

		this.repoEgresos.agregarEgresoNoValidado(this);

		this.resultadoValidacion = new ResultadoValidacion();
	}
	
	public Egreso(Proveedor burguerKing, MedioPago tarjeta) {
		// TODO Auto-generated constructor stub
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
		this.repoEgresos.agregarEgresoNoValidado(this);
		this.getPresupuestos().add(presupuesto);
	}

	public List<Presupuesto> getPresupuestos() {
		return this.presupuestos;
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

	public void aniadirItem(Item lechuga) {
		// TODO Auto-generated method stub

	}

	public Proveedor getProveedorSeleccionado() {
		return this.datosEgreso.getProveedor();
	}

	public Proveedor proveedorCandidatoSegunCriterio() {
		return this.criterioProveedor.seleccionarProveedor(presupuestos);
	}


	private void agregarMensajeSegunEstado(boolean estado, String mensaje) {

		resultadoValidacion.agregarMensaje(mensaje + ": " + estado);
	}

	//todo acá repito código, pero no puedo evitarlo. Ayudaa
	private boolean compraRealizadaSegunAlgunPresupuesto() {
		boolean estado = presupuestos.stream().anyMatch(presupuesto -> presupuesto.getUnProveedor().equals(this.datosEgreso.getProveedor())
				&& presupuesto.getItems().equals(this.items) && presupuesto.valorTotal() == this.valorTotal());
				//todo averiguar si comparar dos listas funciona si los elementos están en distintos ordenes
		agregarMensajeSegunEstado(estado, "Compra realizada segíún un presupuesto");
		return estado;
	}

	private boolean eligioProveedorSegunCriterio() {
		boolean estado =  this.proveedorCandidatoSegunCriterio().equals(this.datosEgreso.getProveedor());
		agregarMensajeSegunEstado(estado,"Proveedor fue elegido según el criterio de " +
				"selección de presupuestos" );
		return estado;
	}

	private boolean tieneCantidadMinimaDePresupuestos() {
		boolean estado = this.presupuestos.size() == cantPresupuestosMinima;
		agregarMensajeSegunEstado(estado, "Compra realizada con cantidad minima de presupuestos");
		return estado;
	}

	private void notificarUsuariosRevisores() {
		resultadoValidacion.actualizarFecha();
		usuariosRevisores.stream().forEach(usuario -> usuario.serNotificado(resultadoValidacion));
		resultadoValidacion = new ResultadoValidacion();
	}

	public boolean egresoValido() {
		boolean estado = this.compraRealizadaSegunAlgunPresupuesto() && this.eligioProveedorSegunCriterio()
				&& this.tieneCantidadMinimaDePresupuestos();
		notificarUsuariosRevisores();
		return estado;

	}
}
