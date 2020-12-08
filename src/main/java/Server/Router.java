package Server;

import dds.gesoc.model.Controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }
    
	public static void configure() {
		Spark.before((request, response) -> {PerThreadEntityManagers.getEntityManager();});
		
		/*Si el usuario no inició sesión, va a ser redirigido a la página de login
		if (!request.pathInfo().contains("/login") && StringUtils.isEmpty(request.cookie("usuario-logueado"))) {
			response.redirect(("/login"));
		}*/
		
		Spark.after((request, response) -> {PerThreadEntityManagers.closeEntityManager();});


		ControllerEntidades controllerEntidades = new ControllerEntidades();
		ControllerEgresos controllerEgresos = new ControllerEgresos();
		ControllerMensajes controllerMensajes = new ControllerMensajes();
		//ControllerUsuarios controllerUsuarios = new ControllerUsuarios(); (esta variable nunca es usada)
		ControllerItems controllerItems = new ControllerItems();
		ControllerPresupuestos controllerPresupuestos = new ControllerPresupuestos();

		Spark.get("/", ControllerRaiz::bienvenida, engine);

		Spark.get("/home", ControllerHome::home, engine);
		Spark.get("/login", ControllerLogin::show, engine);
		Spark.post("/login", ControllerLogin::login, engine);
		Spark.get("/egresos", controllerEgresos::listar, engine);
		Spark.post("/egresos", controllerEgresos::validar);
		Spark.get("/egreso", controllerEgresos::nuevo, engine);
		Spark.get("/egreso/:id", controllerEgresos::mostrar, engine);
		Spark.post("/egreso", controllerEgresos::crear);    
		Spark.post("/egreso/:id", controllerEgresos::modificar);
		Spark.get("/egreso/:id/items", controllerItems::listarItems, engine);
		Spark.get("/egreso/:id/item", controllerItems::nuevoItem, engine);
		Spark.get("/egreso/:id/item/:id", controllerItems::mostrarItem, engine);
		Spark.post("/egreso/:id/item", controllerItems::crearItem);    
		Spark.post("/egreso/:id/item/:id", controllerItems::modificarItem);
		Spark.get("/egreso/:id/presupuestos", controllerPresupuestos::listarPresupuestos, engine);
		Spark.get("/egreso/:id/presupuesto", controllerPresupuestos::nuevoPresupuesto, engine);
		Spark.post("/egreso/:id/presupuesto", controllerPresupuestos::crearPresupuesto);    
		Spark.post("/egreso/:id/presupuesto/:id", controllerPresupuestos::modificarPresupuesto);
		Spark.get("/entidades", controllerEntidades::listar, engine);
		Spark.get("/entidad", controllerEntidades::nuevo, engine);
		Spark.get("/entidad/:id", controllerEntidades::mostrar, engine);
		Spark.post("/entidades", controllerEntidades::crearEntidadBase);
		Spark.post("/entidades", controllerEntidades::crearEntidadJuridica);
		Spark.get("/mensajes", controllerMensajes::listar, engine);
		Spark.get("/mensajes/:id", controllerMensajes::mostrar, engine);
		Spark.get("/usuario", ControllerUsuarios::mostrar, engine);
		Spark.get("/organizaciones", ControllerOrganizaciones::mostrar, engine);
	}
}