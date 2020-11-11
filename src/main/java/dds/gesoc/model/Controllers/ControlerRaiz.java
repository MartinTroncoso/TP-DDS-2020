package dds.gesoc.model.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControlerRaiz {
    public static ModelAndView bienvenida(Request req, Response res) {
        return new ModelAndView(null,"/root.hbs");
    }

}
