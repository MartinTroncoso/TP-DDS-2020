package Server;

import java.math.BigDecimal;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import db.EntityManagerHelper;
import dds.gesoc.model.egresos.CriterioSeleccionProveedor;
import dds.gesoc.model.egresos.DireccionPostal;
import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.egresos.TipoMedioPago;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.mercadolibre.ServicioGeograficoMercadoLibre;
import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoEntidades;
import dds.gesoc.model.repositorios.RepoMonedas;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	public static void main(String[] args) {
		new Bootstrap().init();
		System.exit(0);
	}
	
	public void init(){
		withTransaction(() ->{

			/*
			 * Persistencia de monedas (se llama a la api)
			 */
			
			ServicioGeograficoMercadoLibre mercadoLibreService = new ServicioGeograficoMercadoLibre();
			List<Moneda> monedas = mercadoLibreService.obtenerTodasLasMonedas();
			monedas.forEach(moneda -> persist(moneda));
			
			/*
			 * Persistencia de Medios de Pago
			 */
			
			MedioPago tarjetaDeCredito = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO, "4444444444444444");
			persist(tarjetaDeCredito);
			
			/*
			 * Persistencia de un proveedor
			 */
			
			Proveedor proveedor = new Proveedor("Martín","42498956","Argentina", "Cordoba", "Villa Carlos Paz", "Arrecifes 2140");
			persist(proveedor);
			
			/*
			 * Persistencia de un egreso
			 */
			
			//esto hay que funarlo
			Documento documento = new Documento("Tarjeta chica",2703);
			persist(documento);
			/*MedioPago medioPago = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
			persist(medioPago);*/
			/*Moneda moneda = new Moneda("peso","no para de engordar","$");
			persist(moneda);*/
			
			Moneda moneda = RepoMonedas.getInstance().buscar(1);
			Egreso egreso = new Egreso();
			egreso.setProveedor(proveedor);
			egreso.setDocComercial(documento);
			egreso.setMedioPago(tarjetaDeCredito);
			egreso.setMoneda(moneda);
			egreso.setCantPresupuestosMinima(3);
			egreso.setCriterioProveedor(CriterioSeleccionProveedor.MENOR_VALOR);
			persist(egreso);
			
			/*
			 * Persistencia de categorías
			 */
			
			Categoria ong = new Categoria("ong");
			Categoria judicial = new Categoria("judicial");
			Categoria industrial = new Categoria("industrial");
			persist(ong);
			persist(judicial);
			persist(industrial);
			
			/*
			 * Persistencia de una entidad
			 */

			EntidadBase entidadBase = new EntidadBase("juan sa", ong, 2000.00, null);
			entidadBase.setDescripcion("Organización no Gubernamental");
			persist(entidadBase);
			
			/*
			 * Persistencia de un usuario
			 */
			
			Usuario usuario = new Usuario("Nico","holaquetal",TipoUsuario.ADMINISTRADOR,entidadBase);
			persist(usuario);
			
			Entidad entidad = RepoEntidades.getInstance().buscar(1);
			Egreso egresoo = RepoEgresos.getInstance().buscar(1);
			Usuario usuarioSuscriptor = new Usuario("Damián", "holamuybuenas", TipoUsuario.ADMINISTRADOR, entidad);
			egreso.agregarUsuarioRevisor(usuarioSuscriptor);
			merge(egresoo);
		});
	}
}