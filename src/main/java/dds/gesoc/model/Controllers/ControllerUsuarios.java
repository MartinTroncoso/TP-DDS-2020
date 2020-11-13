package dds.gesoc.model.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerUsuarios {
    public static ModelAndView mostrar(Request req, Response res) {
        return new ModelAndView(null,"/usuarios/show.hbs");
    }

}
