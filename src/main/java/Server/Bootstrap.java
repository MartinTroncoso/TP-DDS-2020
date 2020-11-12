package Server;

import java.math.BigDecimal;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	public static void main(String[] args) {
		new Bootstrap().init();
	}
	
	public void init(){
		withTransaction(() ->{
			Proveedor proveedor = new Proveedor("Martín");
			persist(proveedor);
			
			Egreso egreso = new Egreso();
			egreso.setProveedor(proveedor);
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
		});
	}
}

