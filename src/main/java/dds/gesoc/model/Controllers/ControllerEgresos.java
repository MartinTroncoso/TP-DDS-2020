package dds.gesoc.model.Controllers;

import java.util.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.DatosEgreso;
import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.egresos.TipoMedioPago;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoMonedas;
import dds.gesoc.model.repositorios.RepoProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerEgresos implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView listar(Request req, Response res) {
		Map<String, List<Egreso>> modelo = new HashMap<>();
		List<Egreso> egresos = RepoEgresos.getInstance().getEgresos();
		modelo.put("egresos", egresos);
		return new ModelAndView(modelo,"/egresos/egresos.hbs");
	}
	
	public ModelAndView nuevo(Request req, Response res) {
		//TODO: al crear un egreso hay que inicializar los repositorios de proveedores, presupuestos, items y etiquetas

		Map<String, Object> modelo = new HashMap<>();
		List<Proveedor> proveedores = RepoProveedores.getInstance().getProveedores();
		List<Moneda> monedas = RepoMonedas.getInstance().getMonedas();
		
		
		modelo.put("proveedores",proveedores);
		modelo.put("monedas", monedas);

		return new ModelAndView(modelo,"/egresos/egreso.hbs");
	}
	
	public ModelAndView mostrar(Request req, Response res) {
		Map<String, Egreso> model = new HashMap<>();
		String id = req.params("id");
		
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(id));
		model.put("egreso", egreso);
		return new ModelAndView(model,"/egresos/egreso.hbs");
	}
	
	private void asignarAtributosA(Egreso egreso, Request request){     
		if(request.queryParams("proveedor") != null){
			Proveedor proveedor = RepoProveedores.getInstance().buscar(new Integer(request.queryParams("proveedor")));
        	egreso.setProveedor(proveedor);
        }
		
        if(request.queryParams("tipoDocumento") != null){
        	egreso.getDocComercial().setTipo(request.queryParams("tipoDocumento"));
        }
        
        if(request.queryParams("numeroDocumento") != null){
            int numeroDocumento = new Integer(request.queryParams("numeroDocumento"));
            egreso.getDocComercial().setNumero(numeroDocumento);
        }
        
        if(request.queryParams("tipoMedioPago") != null){
        	TipoMedioPago tipo = TipoMedioPago.valueOf(request.queryParams("tipoMedioPago"));
            egreso.getMedioPago().setTipo(tipo);
        }
        
        if(request.queryParams("numeroMedioPago") != null){
            egreso.getMedioPago().setNumero(request.queryParams("numeroMedioPago"));
        }
        
        if(request.queryParams("moneda") != null){
        	Moneda moneda = RepoMonedas.getInstance().buscar(new Integer(request.queryParams("moneda")));
        	egreso.setMoneda(moneda);
        }
        
        if(request.queryParams("cantPresupuestosMinima") != null){
            egreso.setCantPresupuestosMinima(new Integer(request.queryParams("cantPresupuestosMinima")));
        }
        
        if(request.queryParams("etiquetas") != null) {
        	String etiquetas = request.queryParams("etiquetas");
        	List<String> listaDeEtiquetas = Arrays.asList(etiquetas.split(","));
        	listaDeEtiquetas.forEach(etiqueta -> egreso.agregarEtiqueta(etiqueta));
        }
    }
	
	public Response crear(Request request, Response response){
		Egreso egreso = new Egreso();
		Documento documento = new Documento();
		MedioPago medioPago = new MedioPago();
		Moneda moneda = new Moneda();
		egreso.setDocComercial(documento);
		egreso.setMedioPago(medioPago);
		egreso.setMoneda(moneda);
		
		this.asignarAtributosA(egreso, request);

		withTransaction(() ->{
			RepoEgresos.getInstance().agregarEgresoNuevo(egreso);
		});
		response.redirect("/egresos");
		return response;
	}
	
	public Response modificar(Request req, Response res) {
		Egreso egreso = RepoEgresos.getInstance().buscar(new Integer(req.params("id")));
        asignarAtributosA(egreso, req);
        withTransaction(() ->{
        	RepoEgresos.getInstance().modificar(egreso);
        });
        res.redirect("/egresos");
        return res;
	}

	public Response validar(Request request, Response response) {
		withTransaction(() ->{
			RepoEgresos.getInstance().validarEgresos();
		});
		response.redirect("/egresos");
		return response;
	}
}