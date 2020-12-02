package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.egresos.Presupuesto;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.RepoItems;
import dds.gesoc.model.egresos.RepoPresupuestos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerPresupuestos implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView listarPresupuestos(Request req, Response res) {
		Map<String, Egreso> modelo = new HashMap<>();
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		modelo.put("egreso", egreso);
		return new ModelAndView(modelo,"/egresos/presupuestos.hbs");
	}
	
	public ModelAndView nuevoPresupuesto(Request req, Response res) {
		return new ModelAndView(null,"/egresos/presupuesto.hbs");
	}
	
	public Response crearPresupuesto(Request request, Response response){
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(request.params("id")));
		Presupuesto presupuesto = new Presupuesto();
		
		asignarAtributosA(request, presupuesto);
		egreso.agregarPresupuesto(presupuesto);

		withTransaction(() ->{
			RepoPresupuestos.getInstance().agregarPresupuesto(presupuesto);
			RepoEgresos.getInstance().modificar(egreso);
		});
		response.redirect("/egresos/" + egreso.getId() + "/presupuestos");
		return response;
	}

	public void asignarAtributosA(Request request, Presupuesto presupuesto) {
		if(request.queryParams("tipoDocumento") != null){
			presupuesto.getDocComercial().setTipo(request.queryParams("tipoDocumento"));
        }
        
        if(request.queryParams("numeroDocumento") != null){
            presupuesto.getDocComercial().setNumero(new Integer(request.queryParams("numeroDocumento")));
        }
        
        if(request.queryParams("detalle") != null){
            presupuesto.setDetalle((request.queryParams("detalle")));
        }
        
        if(request.queryParams("nombre") != null){
        	presupuesto.getProveedor().setNombreORazonSocial(request.queryParams("nombre"));
        }
        
        if(request.queryParams("dni") != null){
        	presupuesto.getProveedor().setDniOCuit(request.queryParams("dni"));
        }
        
        if(request.queryParams("direccion") != null){
        	presupuesto.getProveedor().setDireccion(request.queryParams("direccion"));
        }
        
        if(request.queryParams("ciudad") != null){
        	presupuesto.getProveedor().setCiudad(request.queryParams("ciudad"));
        }
        
        if(request.queryParams("provincia") != null){
        	presupuesto.getProveedor().setProvincia(request.queryParams("provincia"));
        }
        
        if(request.queryParams("pais") != null){
        	presupuesto.getProveedor().setPais(request.queryParams("pais"));
        }
	}
	
	public Response modificarPresupuesto(Request req, Response res) {
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		Presupuesto presupuesto = RepoPresupuestos.getInstance().buscar(new Integer(req.params("id")));
        asignarAtributosA(req, presupuesto);
        RepoPresupuestos.getInstance().modificar(presupuesto);
        res.redirect("/egreso/" + egreso.getId() + "/presupuestos");
        return res;
	}
}
