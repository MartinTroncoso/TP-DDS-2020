package dds.gesoc.model.Controllers;

import dds.gesoc.model.repositorios.RepoUsuarios;
import dds.gesoc.model.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ControllerUsuarios {
    public static ModelAndView mostrar(Request req, Response res) {
        int id = req.session().attribute("usuario-logueado");
        Usuario usuario = RepoUsuarios.getInstance().buscarUsuarioPorId(id);

        Map<String, String> modelo = new HashMap<>();
        modelo.put("id", Integer.toString(id));
        modelo.put("nombre", usuario.getNombreUsuario());
        modelo.put("tipo", usuario.getTipoUsuario().toString());

        return new ModelAndView(modelo,"/usuarios/show.hbs");
    }

}
