package dds.gesoc.model.Controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoUsuarios;
import dds.gesoc.model.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerSuscripciones implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView listar(Request req, Response res) {
		
		Map<String, List<Egreso>> modelo = new HashMap<>();
		List<Egreso> egresos = RepoEgresos.getInstance().getEgresos();
		
		int idUsuario = req.session().attribute("usuario-logueado");
		Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorId(idUsuario);
		
		Map<Boolean, List<Egreso>> egresoMap = egresos
				.stream()
				.collect(Collectors.partitioningBy(e -> e.getUsuariosRevisores().contains(usuario)));
		
		List<Egreso> egresosSuscriptos = egresoMap.get(true);
		List<Egreso> egresosNoSuscriptos = egresoMap.get(false);
		
		modelo.put("egresosSuscriptos", egresosSuscriptos);
		modelo.put("egresosNoSuscriptos", egresosNoSuscriptos);
		return new ModelAndView(modelo,"/suscripciones/suscripciones.hbs");
	}
	
	public Response suscribirse(Request req, Response res) {
		
		int idUsuario = req.session().attribute("usuario-logueado");
		Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorId(idUsuario);
		
		String idEgreso = req.params("id");
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(idEgreso));
		
		withTransaction(() ->{
			egreso.agregarUsuarioRevisor(usuario);
			entityManager().merge(egreso);
		});
		
		res.redirect("/suscripciones");
		return res;
	}
	
	public Response desuscribirse(Request req, Response res) {
		
		int idUsuario = req.session().attribute("usuario-logueado");
		Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorId(idUsuario);
		
		String idEgreso = req.params("id");
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(idEgreso));
		
		withTransaction(() ->{
			egreso.eliminarUsuarioRevisor(usuario);
			entityManager().merge(egreso);
		});
		
		res.redirect("/suscripciones");
		return res;
	}
}