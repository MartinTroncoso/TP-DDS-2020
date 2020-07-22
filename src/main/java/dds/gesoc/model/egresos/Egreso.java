package dds.gesoc.model.egresos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.gesoc.exceptions.UsuarioRevisorException;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.usuarios.Usuario;

public class Egreso {

	private DatosEgreso datosEgreso;

	private LocalDate fechaOperacion;
	private List<Item> items;
	private List<Presupuesto> presupuestos;
    private Moneda moneda;
	private int cantPresupuestosMinima;
	private CriterioSeleccionProveedor criterioProveedor;
	private List<Usuario> usuariosRevisores;
	private RepoEgresos repoEgresos;
	private ResultadoValidacion resultadoValidacion;
	private Etiqueta etiqueta;

	private boolean valido;  //TODO esto puede causar inconsistencia. Debería ser calculable


	public Egreso(DatosEgreso datosEgreso, Moneda moneda, int cantPresupuestosMinima, CriterioSeleccionProveedor criterioProveedor) {
		this.datosEgreso = datosEgreso;

		//TODO: ver como validar si la moneda es alguna de las que nos proporciona la api
		this.fechaOperacion = LocalDate.now();
		this.items = new ArrayList<Item>();
		this.presupuestos = new ArrayList<>();
		this.moneda = moneda;
		this.cantPresupuestosMinima = cantPresupuestosMinima;
		this.criterioProveedor = criterioProveedor;
		this.usuariosRevisores = new ArrayList<>();
		this.repoEgresos = RepoEgresos.getInstance();
		this.repoEgresos.agregarEgresoNuevo(this);
		this.setValido(false);
		this.resultadoValidacion = new ResultadoValidacion();
	}


	public String toString() {
		return datosEgreso.toString() + "Valor total: " + valorTotal();
	}

	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void borrarEtiqueta() {
		this.etiqueta = null;
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

    public void setMiProveedor(Proveedor miProveedor) {
        this.datosEgreso.setProveedor(miProveedor);
    }
    
	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

    public void agregarPresupuesto(Presupuesto presupuesto){
		this.setValido(false);
		this.getPresupuestos().add(presupuesto);
	}

	public List<Presupuesto> getPresupuestos() {
		return this.presupuestos;
	}

	public Proveedor getProveedor() {
		return this.datosEgreso.getProveedor();
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
	
	public ValorMonetario valorTotal() {
        return new ValorMonetario(this.getItems().stream().mapToDouble(Item::getValor).sum(), moneda);
	}


	public double valorTotalEnMoneda(Moneda unaMoneda) {
		return valorTotal().getCantidadConvertida(unaMoneda);
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

	public Proveedor getProveedorSeleccionado() {
		return this.datosEgreso.getProveedor();
	}

	public Proveedor proveedorCandidatoSegunCriterio() {
		return this.criterioProveedor.seleccionarProveedor(this.presupuestos);
	}


	private void agregarMensajeSegunEstado(boolean estado, String mensaje) {

		resultadoValidacion.agregarMensaje(mensaje + ": " + estado);
	}

	//todo acá repito código, pero no puedo evitarlo. Ayudaa
	public boolean compraRealizadaSegunAlgunPresupuesto() {
        return this.presupuestos.stream().anyMatch(presupuesto -> presupuesto.compraRealizadaSegunEstePresupuesto(this));
				//todo averiguar si comparar dos listas funciona si los elementos están en distintos ordenes
	}

	public boolean eligioProveedorSegunCriterio() {
        return  this.proveedorCandidatoSegunCriterio().equals(this.datosEgreso.getProveedor());
	}

	public boolean tieneCantidadMinimaDePresupuestos() {
        return this.presupuestos.size() >= cantPresupuestosMinima;
	}

	public void notificarUsuariosRevisores() {
		resultadoValidacion.actualizarFecha();
		usuariosRevisores.forEach(usuario -> usuario.serNotificado(resultadoValidacion));
		resultadoValidacion = new ResultadoValidacion();
	}

/*
* egresoValido devuelve true o false según la validez del egreso sin notificar a los usuarios
* */
	public boolean egresoValido() {
        return compraRealizadaSegunAlgunPresupuesto() && eligioProveedorSegunCriterio()
                && tieneCantidadMinimaDePresupuestos();
    }

/*
* Validar notifica a los usuarios el resultado de una validación
* */
	public void validar() {

		boolean estadoValidacion = this.egresoValido();
        agregarMensajeSegunEstado(estadoValidacion, "----\nEgreso valido");
        agregarMensajeSegunEstado(this.compraRealizadaSegunAlgunPresupuesto(), "Compra realizada segíún un presupuesto");
        agregarMensajeSegunEstado(this.eligioProveedorSegunCriterio(),"Proveedor fue elegido según el criterio de " +
                "selección de presupuestos" );
        agregarMensajeSegunEstado(this.tieneCantidadMinimaDePresupuestos(), "Compra realizada con cantidad minima de presupuestos");
		notificarUsuariosRevisores();
		this.setValido(estadoValidacion);
	}

}
