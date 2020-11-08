package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.ResultadoValidacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerMensajes{
	  public ModelAndView mostrar(Request req, Response res){
	        Map<String, ResultadoValidacion> model = new HashMap<>();
	        String id = req.params("id");
	        
	        ResultadoValidacion proyecto = RepositorioProyectos.instancia.buscar(Long.parseLong(id));
	        model.put("proyecto", proyecto);
	        //Para conseguir un dato de la sesión, como el nombre del usuario
	        model.put("usuario", req.session().attribute("usuario-logueado"));
	        return new ModelAndView(model, "proyectos/show.hbs");
	    }
	
	public static ModelAndView listar(Request req, Response res) {
		Map<String, List<ResultadoValidacion>> modelo = new HashMap<>();
		List<ResultadoValidacion> bandejaDeMensajes = RepoEgresos.getInstance().getEgresos();
		modelo.put("bandejaDeMensajes", bandejaDeMensajes);
		return new ModelAndView(modelo,"/mensajes/mensajes.hbs");
	}
}
