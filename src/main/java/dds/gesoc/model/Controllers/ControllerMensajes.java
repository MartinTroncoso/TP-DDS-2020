package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.ResultadoValidacion;
import dds.gesoc.model.usuarios.RepoUsuarios;
import dds.gesoc.model.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerMensajes implements WithGlobalEntityManager, TransactionalOps {

	public ModelAndView mostrar(Request req, Response res) {
		
		Map<String, Object> model = new HashMap<>();
		
		// String id = req.session().attribute("usuario-logueado");
		int idMensaje = Integer.parseInt(req.params("id"));
		
		// TODO: Hardcodeado 1 por el momento
		Usuario unUsuario = RepoUsuarios.getInstance().buscarUsuarioPorId(1);
		List<ResultadoValidacion> resultados = unUsuario.getBandejaDeMensajes();
		ResultadoValidacion resultadoElegido = resultados.stream().filter(r -> r.getId() == idMensaje).findFirst().orElseGet(null);
		
		model.put("resultado", resultadoElegido);
		
		return new ModelAndView(model, "/mensajes/show.hbs");
	}

	public ModelAndView listar(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		
		// String id = req.session().attribute("usuario-logueado");
		// TODO: Hardcodeado 1 por el momento
		Usuario unUsuario = RepoUsuarios.getInstance().buscarUsuarioPorId(1);
		List<ResultadoValidacion> resultados = unUsuario.getBandejaDeMensajes();
		model.put("mensajes", resultados);
		

		return new ModelAndView(model, "/mensajes/mensajes.hbs");
	}
}
