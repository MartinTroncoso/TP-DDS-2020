package dds.gesoc.model.egresos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.exceptions.EtiquetaYaExistenteException;
import dds.gesoc.exceptions.UsuarioRevisorException;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.usuarios.Usuario;

@Entity
@Table(name = "egreso")
public class Egreso extends EntidadPersistente{
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "proveedor_id", referencedColumnName = "id")
	private Proveedor proveedor;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "documento_id")
	private Documento docComercial;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mediopago_id", referencedColumnName = "id")
	private MedioPago medioPago;
	
	@Column(columnDefinition = "DATE")
	private LocalDate fechaOperacion = LocalDate.now();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "egreso_id")
	private List<Item> items = new ArrayList<>();
	
	@OneToMany(mappedBy = "egresoAsociado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Presupuesto> presupuestos = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "moneda_id", referencedColumnName = "id")
    private Moneda moneda;
	
	@Column
	private int cantPresupuestosMinima;
	
	@Transient //TODO: ver si es mapeable
	private CriterioSeleccionProveedor criterioProveedor;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Usuario> usuariosRevisores;
	
	@Transient
	private RepoEgresos repoEgresos;
	
	@Transient //TODO: ver si hay relacion entre ResultadoValidacion y Egreso
	private ResultadoValidacion resultadoValidacion;
	
	@ElementCollection
	@Column(name="nombre_etiqueta")
	private Set<String> etiquetas = new HashSet<>();
	
	@Column
	private boolean valido;  //TODO esto puede causar inconsistencia. Deberia ser calculable

	public Egreso(){
		//this.repoEgresos.agregarEgresoNuevo(this);
	}

	public Egreso(DatosEgreso datosEgreso, Moneda moneda, int cantPresupuestosMinima, CriterioSeleccionProveedor criterioProveedor) {
		this.proveedor = datosEgreso.getProveedor();
		this.docComercial = datosEgreso.getDocComercial();
		this.medioPago = datosEgreso.getMedioPago();

		//TODO: ver como validar si la moneda es alguna de las que nos proporciona la api
		this.moneda = moneda;
		this.cantPresupuestosMinima = cantPresupuestosMinima;
		this.criterioProveedor = criterioProveedor;
		this.usuariosRevisores = new ArrayList<>();
		this.repoEgresos = RepoEgresos.getInstance();
//		this.repoEgresos.agregarEgresoNuevo(this);
		this.setValido(false);
		this.resultadoValidacion = new ResultadoValidacion();
	}


	public Set<String> getEtiquetas() {
		return this.etiquetas;
	}

	public void agregarEtiqueta(String etiqueta) {
		this.etiquetas.add(etiqueta.toLowerCase());
	}

	public void borrarEtiqueta(String etiqueta) {
		etiquetas.remove(etiqueta.toLowerCase());
	}

	public void agregarItem(Item item) {
		this.items.add(item);
	}
	
	public List<Item> getItems(){
		return this.items;
	}

	public LocalDate getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(LocalDate fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

    public void setMiProveedor(Proveedor miProveedor) {
        this.setProveedor(miProveedor);
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
		return this.proveedor;
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
        return new ValorMonetario(this.getItems().stream().mapToDouble(Item::getMonto).sum(), moneda);
	}


	public double valorTotalEnMoneda(Moneda unaMoneda) {
		return valorTotal().getMontoConvertido(unaMoneda);
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
		return this.getProveedor();
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
        return  this.proveedorCandidatoSegunCriterio().equals(this.getProveedor());
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

		resultadoValidacion.setAsunto("Validacion Egreso: " + getId());
		boolean estadoValidacion = this.egresoValido();
        agregarMensajeSegunEstado(estadoValidacion, "----\nEgreso valido");
        agregarMensajeSegunEstado(this.compraRealizadaSegunAlgunPresupuesto(), "Compra realizada segíún un presupuesto");
        agregarMensajeSegunEstado(this.eligioProveedorSegunCriterio(),"Proveedor fue elegido según el criterio de " + "selección de presupuestos" );
        agregarMensajeSegunEstado(this.tieneCantidadMinimaDePresupuestos(), "Compra realizada con cantidad minima de presupuestos");
		notificarUsuariosRevisores();
		this.setValido(estadoValidacion);
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Documento getDocComercial() {
		return docComercial;
	}

	public void setDocComercial(Documento docComercial) {
		this.docComercial = docComercial;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}
	
	public int getCantidadItems() {
		return items.size();
	}
	
	public int getCantidadPresupuestos() {
		return presupuestos.size();
	}
	
	public int getCantidadEtiquetas() {
		return etiquetas.size();
	}

	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}
	
	public Moneda getMoneda() {
		return moneda;
	}
	
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
}
