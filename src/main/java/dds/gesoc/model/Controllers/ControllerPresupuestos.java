package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Item;
import dds.gesoc.model.egresos.Presupuesto;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoItems;
import dds.gesoc.model.repositorios.RepoMonedas;
import dds.gesoc.model.repositorios.RepoPresupuestos;
import dds.gesoc.model.repositorios.RepoProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerPresupuestos implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView listarPresupuestos(Request req, Response res) {
		Map<String, Object> modelo = new HashMap<>();
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		List<Presupuesto> presupuestos = RepoPresupuestos.getInstance().getPresupuestosDeEgreso(egreso);
		
		modelo.put("egreso", egreso);
		modelo.put("presupuestos", presupuestos);
		return new ModelAndView(modelo,"/egresos/presupuestos.hbs");
	}
	
	public ModelAndView nuevoPresupuesto(Request req, Response res) {
		Map<String, Object> modelo = new HashMap<>();
		List<Proveedor> proveedores = RepoProveedores.getInstance().getProveedores();
		List<Moneda> monedas = RepoMonedas.getInstance().getMonedas();
		
		modelo.put("proveedores",proveedores);
		modelo.put("monedas", monedas);

		return new ModelAndView(modelo,"/egresos/presupuesto.hbs");
	}
	
	public Response crearPresupuesto(Request request, Response response){
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(request.params("id")));
		Presupuesto presupuesto = new Presupuesto();
		Documento docComercial = new Documento();
		Proveedor proveedor = new Proveedor();
		Moneda moneda = new Moneda();
		presupuesto.setDocComercial(docComercial);
		presupuesto.setProveedor(proveedor);
		presupuesto.setMoneda(moneda);
		presupuesto.establecerEgresoAsociado(egreso);
		
		asignarAtributosA(request, presupuesto);
		egreso.agregarPresupuesto(presupuesto);
		
		withTransaction(() ->{
			RepoPresupuestos.getInstance().agregarPresupuesto(presupuesto);
		});
		RepoEgresos.getInstance().modificar(egreso);
		response.redirect("/egreso/" + egreso.getId() + "/presupuestos");
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
        
        if(request.queryParams("montoTotal") != null){
            presupuesto.setMontoTotal(new Integer(request.queryParams("montoTotal")));
        }
        
		if(request.queryParams("proveedor") != null){
			Proveedor proveedor = RepoProveedores.getInstance().buscar(new Integer(request.queryParams("proveedor")));
        	presupuesto.setProveedor(proveedor);
        }
        
        if(request.queryParams("moneda") != null){
        	Moneda moneda = RepoMonedas.getInstance().buscar(new Integer(request.queryParams("moneda")));
        	presupuesto.setMoneda(moneda);
        }       
	}
	
	public Response modificarPresupuesto(Request req, Response res) {
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(req.params("id")));
		Presupuesto presupuesto = RepoPresupuestos.getInstance().buscar(Integer.parseInt(req.params("id")));
        asignarAtributosA(req, presupuesto);
        RepoPresupuestos.getInstance().modificar(presupuesto);
        res.redirect("/egreso/" + egreso.getId() + "/presupuestos");
        return res;
	}
}
