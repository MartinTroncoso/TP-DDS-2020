package dds.gesoc.model.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerHome {
	public static ModelAndView home(Request req, Response res) {
		return new ModelAndView(null,"/home.hbs");
	}
}
