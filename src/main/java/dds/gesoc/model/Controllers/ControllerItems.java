package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.RepoItems;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerItems implements WithGlobalEntityManager, TransactionalOps {
	public ModelAndView listarItems(Request req, Response res) {
		Map<String, Egreso> modelo = new HashMap<>();
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		modelo.put("egreso", egreso);
		return new ModelAndView(modelo,"/egresos/items.hbs");
	}
	
	public ModelAndView nuevoItem(Request req, Response res) {
		return new ModelAndView(null,"/egresos/item.hbs");
	}
	
	public Response crearItem(Request request, Response response){
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(request.params("id")));
		Item item = new Item();
		
		asignarAtributosA(request, item);
		egreso.agregarItem(item);

		withTransaction(() ->{
			RepoItems.getInstance().agregarItemNuevo(item);
			RepoEgresos.getInstance().modificar(egreso);
		});
		response.redirect("/egresos/" + egreso.getId() + "/items");
		return response;
	}

	public void asignarAtributosA(Request request, Item item) {
		if(request.queryParams("descripcion") != null){
            item.setDescripcion(request.params("descripcion"));
        }
        
        if(request.queryParams("cantPresupuestosMinima") != null){
            item.getValorItem().setMonto(new Integer(request.params("cantPresupuestosMinima")));
        }
	}
	
	public ModelAndView mostrarItem(Request req, Response res) {
		Map<String, Item> model = new HashMap<>();
		String id = req.params("id");
		
		Item item = RepoItems.getInstance().buscar(Integer.parseInt(id));
		model.put("item", item);
		return new ModelAndView(model,"/egresos/item.hbs");
	}
	
	public Response modificarItem(Request req, Response res) {
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		Item item = RepoItems.getInstance().buscar(new Integer(req.params("id")));
        asignarAtributosA(req, item);
        RepoItems.getInstance().modificar(item);
        res.redirect("/egreso/" + egreso.getId() + "/items");
        return res;
	}
}
