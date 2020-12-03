package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.geografia.ValorMonetario;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoItems;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerItems implements WithGlobalEntityManager, TransactionalOps {
	public ModelAndView listarItems(Request req, Response res) {
		Map<String, Object> modelo = new HashMap<>();
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		List<Item> items = RepoItems.getInstance().getItemsDeEgreso(egreso);
		
		modelo.put("egreso", egreso);
		modelo.put("items", items);
		return new ModelAndView(modelo,"/egresos/items.hbs");
	}
	
	public ModelAndView nuevoItem(Request req, Response res) {
		return new ModelAndView(null,"/egresos/item.hbs");
	}
	
	public Response crearItem(Request request, Response response){
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(request.params("id")));
		Item item = new Item();
		ValorMonetario valorItem = new ValorMonetario();
        item.setValorItem(valorItem);
		asignarAtributosA(request, item);
		egreso.agregarItem(item);

		withTransaction(() ->{
			RepoItems.getInstance().agregarItemNuevo(item);
		});
		RepoEgresos.getInstance().modificar(egreso);
		response.redirect("/egreso/" + egreso.getId() + "/items");
		return response;
	}

	public void asignarAtributosA(Request request, Item item) {
		if(request.queryParams("descripcion") != null){
            item.setDescripcion(request.queryParams("descripcion"));
        }
        
        if(request.queryParams("monto") != null){
            item.getValorItem().setMonto(new Double(request.queryParams("monto")));
        }
	}
	
	public ModelAndView mostrarItem(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		String itemId = req.params("id");
		
		Item item = RepoItems.getInstance().buscar(Integer.parseInt(itemId));
		Egreso egreso = RepoEgresos.getInstance().buscarEgresoDeItem(item);
		
		
		model.put("egreso", egreso);
		model.put("item", item);
		return new ModelAndView(model,"/egresos/item.hbs");
	}
	
	public Response modificarItem(Request req, Response res) {
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		Item item = RepoItems.getInstance().buscar(Integer.parseInt(req.params("id")));
        
		asignarAtributosA(req, item);
        RepoItems.getInstance().modificar(item);
        res.redirect("/egreso/" + egreso.getId() + "/items");
        return res;
	}
}
