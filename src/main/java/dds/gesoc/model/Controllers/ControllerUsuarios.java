package dds.gesoc.model.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerUsuarios {
    public static ModelAndView mostrar(Request req, Response res) {
        String id = req.cookie("usuario-logueado");

        Map<String, String> modelo = new HashMap<>();
        modelo.put("id", id);
        return new ModelAndView(modelo,"/usuarios/show.hbs");
    }

}
