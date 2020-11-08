package dds.gesoc.model.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerLogin {
	public static ModelAndView show(Request req, Response res) {
		return new ModelAndView(null,"/login");
	}
	
	//TODO: CONTROLAR LA CONTRASEÑA
	public static ModelAndView login(Request req, Response res) {
		String nombre = req.queryParams("usuario");
        req.session().attribute("usuario-logueado", nombre);
        res.redirect("/home");
        return null;
	}
}
