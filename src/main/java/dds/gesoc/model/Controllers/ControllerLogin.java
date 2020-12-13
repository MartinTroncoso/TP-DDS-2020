package dds.gesoc.model.Controllers;

import dds.gesoc.model.repositorios.RepoUsuarios;
import dds.gesoc.model.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerLogin {
	public static ModelAndView show(Request req, Response res) {
		return new ModelAndView(null,"/login/login.hbs");
	}
	
	//TODO: CONTROLAR LA CONTRASE�A
	public static ModelAndView login(Request req, Response res) {

		Map<String, Boolean> model = new HashMap<>();
		model.put("esInvalido", true);

		String nombre = req.queryParams("usuario");
		String contrasenia = req.queryParams("contrasenia");

		//Buscar usuario
		Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorNombre(nombre);

		if(usuario == null) {
			return new ModelAndView(model, "/login/login.hbs");
		}
		//llamar al método validarContraseña del usuario encontrado
		boolean resultadoAutenticacion = usuario.autenticarUsuario(contrasenia);


		if(resultadoAutenticacion) {
			req.session().attribute("usuario-logueado", usuario.getId());
			res.redirect("/home");
			return null;
		}
        //else
		return new ModelAndView(model, "/login/login.hbs");
	}
}
