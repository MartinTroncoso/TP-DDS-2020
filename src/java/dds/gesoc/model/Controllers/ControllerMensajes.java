package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.ResultadoValidacion;
import dds.gesoc.model.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerMensajes{
	  public static ModelAndView mostrar(Request req, Response res){
	        Map<String, ResultadoValidacion> model = new HashMap<>();
	        String id = req.session().attribute("usuario-logueado");

	        //Usuario unUsuario = RepoUsuarios.getInstance().busccarPorId(id)
	       //ResultadoValidacion mensaje = usuario.instancia.buscar(Long.parseLong(id));
	        //model.put("mensaje", mensaje);
	        //Para conseguir un dato de la sesi�n, como el nombre del usuario
	       // model.put("usuario", req.session().attribute("usuario-logueado"));
	        return new ModelAndView(model, "/mensajes/show.hbs");
	    }
	
	public static ModelAndView listar(Request req, Response res) {
		Map<String, List<ResultadoValidacion>> modelo = new HashMap<>();
		//List<ResultadoValidacion> bandejaDeMensajes = RepoEgresos.getInstance().getEgresos();
		//modelo.put("bandejaDeMensajes", bandejaDeMensajes);
		//return new ModelAndView(modelo,"/mensajes/mensajes.hbs");
		return new ModelAndView(modelo,"/mensajes/mensajes.hbs");
	}
}
