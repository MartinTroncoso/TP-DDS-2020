package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.ResultadoValidacion;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoUsuarios;
import dds.gesoc.model.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerMensajes implements WithGlobalEntityManager, TransactionalOps {

	public ModelAndView mostrar(Request req, Response res) {
		
		Map<String, Object> model = new HashMap<>();
		
		int idMensaje = Integer.parseInt(req.params("id"));
		
		int idUsuario = req.session().attribute("usuario-logueado");
		Usuario unUsuario = RepoUsuarios.getInstance().buscarUsuarioPorId(idUsuario);
		
		List<ResultadoValidacion> resultados = unUsuario.getBandejaDeMensajes();
		ResultadoValidacion resultadoElegido = resultados.stream().filter(r -> r.getId() == idMensaje).findFirst().orElseGet(null);
		
		model.put("resultado", resultadoElegido);
		
		return new ModelAndView(model, "/mensajes/show.hbs");
	}

	public ModelAndView listar(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		
		int idUsuario = req.session().attribute("usuario-logueado");
		Usuario unUsuario = RepoUsuarios.getInstance().buscarUsuarioPorId(idUsuario);

		List<ResultadoValidacion> resultados;
		if(unUsuario != null) {
			resultados = unUsuario.getBandejaDeMensajes();
			model.put("mensajes", resultados);
		}
		else model.put("mensajes", null);
		return new ModelAndView(model, "/mensajes/mensajes.hbs");
	}
}
