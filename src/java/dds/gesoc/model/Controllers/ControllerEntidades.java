package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.gesoc.model.RepoEntidades.RepoEntidades;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.organizaciones.Entidad;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerEntidades {
	public static ModelAndView listar(Request req, Response res) {
		Map<String, List<Entidad>> modelo = new HashMap<>();
		List<Entidad> entidades = RepoEntidades.getInstance().getEntidades();
		modelo.put("entidades", entidades);
		return new ModelAndView(modelo,"/entidades/entidades.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res) {
		return new ModelAndView(null,"/entidades/entidadesnew.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res) {
		Map<String,Entidad> modelo = new HashMap<>();
		String id = req.params("id");
		Entidad entidad = RepoEntidades.getInstance().buscar(Long.parseLong(id));
		modelo.put("egreso", entidad);
		return new ModelAndView(null,"/entidades/entidadesnew.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res) {
		return new ModelAndView(null,"/egresos/new.hbs");
	}
}
