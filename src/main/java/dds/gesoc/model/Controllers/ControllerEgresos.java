package dds.gesoc.model.Controllers;

import java.util.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.RepoEntidades.RepoCategorias;
import dds.gesoc.model.RepoEntidades.RepositoriosEntidadesH;
import dds.gesoc.model.egresos.DatosEgreso;
import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.RepoProveedores;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;
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
		return new ModelAndView(null,"/egresos/egresosnew.hbs");
	}
	
	public ModelAndView mostrar(Request req, Response res) {
		Map<String, Egreso> model = new HashMap<>();
		String id = req.params("id");
		
		Egreso egreso = RepoEgresos.getInstance().buscar(Integer.parseInt(id));
		model.put("egreso", egreso);
		return new ModelAndView(model,"/egresos/show.hbs");
	}
	
	private void asignarAtributosA(Egreso egreso, Request request){
        if(request.queryParams("nombre") != null){
        	egreso.getProveedor().setNombreORazonSocial(request.queryParams("nombre"));
        }
        
        if(request.queryParams("dni") != null){
        	egreso.getProveedor().setDniOCuit(request.queryParams("dni"));
        }
        
        if(request.queryParams("direccion") != null){
        	egreso.getProveedor().setDireccion(request.queryParams("direccion"));
        }
        
        if(request.queryParams("ciudad") != null){
        	egreso.getProveedor().setCiudad(request.queryParams("ciudad"));
        }
        
        if(request.queryParams("provincia") != null){
        	egreso.getProveedor().setProvincia(request.queryParams("provincia"));
        }
        
        if(request.queryParams("pais") != null){
        	egreso.getProveedor().setPais(request.queryParams("pais"));
        }
        
        if(request.queryParams("tipoDocumento") != null){
        	egreso.getDocComercial().setTipo(request.queryParams("tipoDocumento"));
        }
        
        if(request.queryParams("numeroDocumento") != null){
            int numeroDocumento = new Integer(request.queryParams("numeroDocumento"));
            egreso.getDocComercial().setNumero(numeroDocumento);
        }
        
        if(request.queryParams("numeroMedioPago") != null){
            egreso.getMedioPago().setNumero(request.queryParams("numeroMedioPago"));
        }
        
        if(request.queryParams("cadenaIdentificadora") != null){
            egreso.getMoneda().setCadenaIdentificadora(request.queryParams("cadenaIdentificadora"));
        }
        
        if(request.queryParams("descripcion") != null){
            egreso.getMoneda().setDescripcion(request.queryParams("descripcion"));
        }
        
        if(request.queryParams("simbolo") != null){
            egreso.getMoneda().setSimbolo(request.queryParams("simbolo"));
        }
        
        if(request.queryParams("cantPresupuestosMinima") != null){
            egreso.setCantPresupuestosMinima(new Integer(request.queryParams("cantPresupuestosMinima")));
        }
    }
	
	public ModelAndView crear(Request request, Response response){
		Egreso egreso = new Egreso();
		Proveedor proveedor = new Proveedor();
		Documento documento = new Documento();
		MedioPago medioPago = new MedioPago();
		Moneda moneda = new Moneda();
		egreso.setDocComercial(documento);
		egreso.setMedioPago(medioPago);
		egreso.setProveedor(proveedor);
		egreso.setMoneda(moneda);
		
		this.asignarAtributosA(egreso, request);

		withTransaction(() ->{
			RepoEgresos.getInstance().agregarEgresoNuevo(egreso);
		});
		response.redirect("/egresos");
		return null;
	}
	
	public ModelAndView modificar(Request req, Response res) {
		Egreso egreso = RepoEgresos.getInstance().buscar(new Integer(req.params("id")));
		
		Map<String, Object> modelo = new HashMap<>();
		modelo.put("egreso",egreso);
		return new ModelAndView(modelo,"/egresos/egresosnew.hbs");
	}
	
	public ModelAndView editar(Request req, Response res) {
		return new ModelAndView(null,"/egresos/egresosnew.hbs");
	}
}
