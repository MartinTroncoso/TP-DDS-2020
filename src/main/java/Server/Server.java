package Server;

import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Server {
	public static void main(String[] args){
		Bootstrap.init();
		Spark.port(9000);
		Spark.get("/",(request,response)-> "Holiwis :-D");
		Router.configure();
	}
	
}
