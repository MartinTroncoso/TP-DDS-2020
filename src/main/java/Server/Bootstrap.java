package Server;

import java.math.BigDecimal;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.DireccionPostal;
import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.egresos.TipoMedioPago;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	public static void main(String[] args) {
		new Bootstrap().init();
	}
	
	public void init(){
		withTransaction(() ->{
			Proveedor proveedor = new Proveedor("Martín","42498956","Argentina", "Cordoba", "Villa Carlos Paz", "Arrecifes 2140");
			persist(proveedor);
			
			Documento documento = new Documento("Tarjeta chica",2703);
			persist(documento);
			MedioPago medioPago = new MedioPago(TipoMedioPago.TARJETA_DE_CREDITO);
			persist(medioPago);
			Moneda moneda = new Moneda("peso","no para de engordar","$");
			persist(moneda);
			
			Egreso egreso = new Egreso();
			egreso.setProveedor(proveedor);
			egreso.setDocComercial(documento);
			egreso.setMedioPago(medioPago);
			egreso.setMoneda(moneda);
			egreso.setCantPresupuestosMinima(3);
			persist(egreso);
			
			Categoria ong = new Categoria("ong");
			Categoria judicial = new Categoria("judicial");
			Categoria industrial = new Categoria("industrial");
			persist(ong);
			persist(judicial);
			persist(industrial);
			
			EntidadBase entidadBase = new EntidadBase("juan sa", ong, 2000.00, null);
			entidadBase.setDescripcion("Organización no Gubernamental");
			persist(entidadBase);
			
			Usuario usuario = new Usuario("Nico","holaquetal",TipoUsuario.ADMINISTRADOR,entidadBase);
			persist(usuario);
		});
	}
}

