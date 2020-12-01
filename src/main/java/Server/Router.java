package Server;

import dds.gesoc.model.Controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();

		Spark.staticFiles.location("/public");
/*		Spark.before((request, response) -> {PerThreadEntityManagers.getEntityManager();});
*Si el usuario no inició sesión, va a ser redirigido a la página de login
*				if (!request.pathInfo().contains("/login") &&
		//			StringUtils.isEmpty(request.cookie("usuario-logueado"))) {
		//					response.redirect(("/login"));
		//	}
*
*  */
//		Spark.after((request, response) -> {PerThreadEntityManagers.closeEntityManager();});




		ControllerEntidades controllerEntidades = new ControllerEntidades();
		ControllerEgresos controllerEgresos = new ControllerEgresos();
		ControllerMensajes controllerMensajes = new ControllerMensajes();
		ControllerUsuarios controllerUsuarios = new ControllerUsuarios();
		ControllerItems controllerItems = new ControllerItems();

		Spark.get("/", ControllerRaiz::bienvenida, engine);

		Spark.get("/home", ControllerHome::home, engine);
		Spark.get("/login", ControllerLogin::show, engine);
		Spark.post("/login", ControllerLogin::login, engine);
		Spark.get("/egresos", controllerEgresos::listar, engine);
		Spark.get("/egreso", controllerEgresos::nuevo, engine);
		Spark.get("/egreso/:id", controllerEgresos::mostrar, engine);
		Spark.post("/egreso", controllerEgresos::crear);    
		Spark.post("/egreso/:id", controllerEgresos::modificar);
		Spark.get("/egreso/:id/items", controllerItems::listarItems, engine);
		Spark.get("/egreso/:id/item", controllerItems::nuevoItem, engine);
		Spark.get("/egreso/:id/item/:id", controllerItems::mostrarItem, engine);
		Spark.post("/egreso/:id/item", controllerItems::crearItem);    
		Spark.post("/egreso/:id/item/:id", controllerItems::modificarItem);
		Spark.get("/entidades", controllerEntidades::listar, engine);
		Spark.get("/entidad", controllerEntidades::nuevo, engine);
		Spark.get("/entidad/:id", controllerEntidades::mostrar, engine);
		Spark.post("/entidad", controllerEntidades::crearEntidadBase);
		Spark.post("/entidad", controllerEntidades::crearEntidadJuridica);
		Spark.get("/mensajes", controllerMensajes::listar, engine);
		Spark.get("/mensajes/:id", controllerMensajes::mostrar, engine);
		Spark.get("/usuario", ControllerUsuarios::mostrar, engine);
		Spark.get("/organizaciones", ControllerOrganizaciones::mostrar, engine);
	}
}
