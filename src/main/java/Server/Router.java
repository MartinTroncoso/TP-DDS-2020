package Server;

import dds.gesoc.model.Controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;


import dds.gesoc.model.Controllers.ControllerEgresos;
import dds.gesoc.model.Controllers.ControllerEntidades;
import dds.gesoc.model.Controllers.ControllerHome;
import dds.gesoc.model.Controllers.ControllerLogin;
import dds.gesoc.model.Controllers.ControllerMensajes;
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
//		Spark.before((request, response) -> {PerThreadEntityManagers.getEntityManager();});
//		Spark.after((request, response) -> {PerThreadEntityManagers.closeEntityManager();});




		ControllerEntidades controllerEntidades = new ControllerEntidades();
		ControllerEgresos controllerEgresos = new ControllerEgresos();
		ControllerMensajes controllerMensajes = new ControllerMensajes();

		Spark.get("/", ControllerRaiz::bienvenida, engine);

		Spark.get("/home", ControllerHome::home, engine);
		Spark.get("/login", ControllerLogin::show, engine);
		Spark.post("/login", ControllerLogin::login, engine);
		Spark.get("/egresos", controllerEgresos::listar, engine);
		Spark.get("/egresos/new", controllerEgresos::nuevo, engine);
		Spark.get("/egresos/:id", controllerEgresos::mostrar, engine);
		Spark.post("/egresos", controllerEgresos::crear);
		Spark.get("/egresos/:id", controllerEgresos::modificar, engine);    
		Spark.post("/egresos/:id", controllerEgresos::editar, engine);
		Spark.get("/entidades", controllerEntidades::listar, engine);
		Spark.get("/entidades/new", controllerEntidades::nuevo, engine);
		Spark.get("/entidades/:id", controllerEntidades::mostrar, engine);
		Spark.post("/entidades", controllerEntidades::crearEntidadBase);
		Spark.post("/entidades", controllerEntidades::crearEntidadJuridica);
		Spark.get("/mensajes", controllerMensajes::listar, engine);
		Spark.get("/mensajes/:id", controllerMensajes::mostrar, engine);
	}
}
