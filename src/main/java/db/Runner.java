package db;

import javax.persistence.EntityManager;

import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.ResultadoValidacion;
import dds.gesoc.model.egresos.TipoMedioPago;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.organizaciones.Empresa;
import dds.gesoc.model.organizaciones.SectorServicios;
import dds.gesoc.model.usuarios.RepoUsuarios;
import dds.gesoc.model.usuarios.TipoUsuario;
import dds.gesoc.model.usuarios.Usuario;

public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerHelper.beginTransaction();
		
		Empresa empresaEjemplo = new Empresa("mc Donaldo", null, 0, "Arcos Dorados SRL", "27-12345678-1",
				"Av. Corrientes 5600", new SectorServicios(), 150000);
		Usuario usuario = new Usuario("root", "administrador1234", TipoUsuario.ADMINISTRADOR, empresaEjemplo);
		ResultadoValidacion resultado = new ResultadoValidacion();
		resultado.setAsunto("Asunto #1");
		resultado.agregarMensaje("Mensajeee");
		resultado.agregarMensaje("Hola2");
		resultado.actualizarFecha();
		usuario.serNotificado(resultado);
		
		RepoUsuarios.getInstance().agregarUsuario(usuario);

		EntityManagerHelper.commit();
		EntityManagerHelper.closeEntityManager();
		System.exit(0);
	}
}

//Moneda moneda = new Moneda("asdasdas", "Moneda argentina", "$");
//Moneda moneda2 = new Moneda("asdasxzdas", "Moneda generica", "$$");
//
//ValorMonetario valor = new ValorMonetario(1333, moneda);
//Item item = new Item("Este es un item de prueba", valor);
//
//MedioPago medio = new MedioPago(TipoMedioPago.TARJETA_DE_DEBITO, "1111222233334444");
//
//EntityManagerHelper.persist(moneda);
//EntityManagerHelper.persist(item);
//EntityManagerHelper.persist(moneda2);
//EntityManagerHelper.persist(medio);