package dds.gesoc.model.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerOrganizaciones {
    public static ModelAndView mostrar(Request req, Response res) {
        return new ModelAndView(null,"/organizaciones/show.hbs");
    }
}
