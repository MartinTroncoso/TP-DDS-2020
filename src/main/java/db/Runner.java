package db;

import javax.persistence.EntityManager;

import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.TipoMedioPago;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;

public class Runner {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		EntityManagerHelper.beginTransaction();
//		
//		Moneda moneda = new Moneda("asdasdas", "Moneda argentina", "$");
//		Moneda moneda2 = new Moneda("asdasxzdas", "Moneda generica", "$$");
//		
//		ValorMonetario valor = new ValorMonetario(1333, moneda);
//		Item item = new Item("Este es un item de prueba", valor);
//		
//		MedioPago medio = new MedioPago(TipoMedioPago.TARJETA_DE_DEBITO, "1111222233334444");
//		
//		EntityManagerHelper.persist(moneda);
//		EntityManagerHelper.persist(item);
//		EntityManagerHelper.persist(moneda2);
//		EntityManagerHelper.persist(medio);
//		
//		EntityManagerHelper.commit();
//		EntityManagerHelper.closeEntityManager();
//		System.exit(0);
//	}
}
