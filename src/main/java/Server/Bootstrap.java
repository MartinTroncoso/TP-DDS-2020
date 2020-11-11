package Server;

import java.math.BigDecimal;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Proveedor;


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
		});
	}
}

