package Server;

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

		Spark.get("/",(request,response)-> "Holiwis :-D");
		Spark.get("/home", ControllerHome::home, engine);
		Spark.get("/login", ControllerLogin::show, engine);
		Spark.post("/login", ControllerLogin::login, engine);
		Spark.get("/egresos", ControllerEgresos::listar, engine);
		Spark.get("/egresos/new", ControllerEgresos::nuevo, engine);
		Spark.get("/egresos/:id", ControllerEgresos::mostrar, engine);
		Spark.post("/egresos", ControllerEgresos::crear);
		Spark.get("/egresos/:id", ControllerEgresos::modificar, engine);    
		Spark.post("/egresos/:id", ControllerEgresos::editar, engine);
		Spark.get("/entidades", ControllerEntidades::listar, engine);
		Spark.get("/entidades/new", ControllerEntidades::nuevo, engine);
		Spark.get("/entidades/:id", ControllerEntidades::mostrar, engine);
		Spark.post("/entidades", ControllerEntidades::crear);
		Spark.get("/mensajes", ControllerMensajes::listar, engine);
		Spark.get("/mensajes/:id", ControllerMensajes::mostrar, engine);
	}
}
