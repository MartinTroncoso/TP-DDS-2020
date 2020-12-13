package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.egresos.Documento;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.MedioPago;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.geografia.Moneda;
import dds.gesoc.model.repositorios.RepoEgresos;
import dds.gesoc.model.repositorios.RepoProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerProveedores implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView listarProveedores(Request req, Response res) {
		Map<String, List<Proveedor>> modelo = new HashMap<>();
		List<Proveedor> proveedores = RepoProveedores.getInstance().getProveedores();
		modelo.put("proveedores", proveedores);
		return new ModelAndView(modelo,"/egresos/proveedores.hbs");
	}
	
	public ModelAndView nuevo(Request req, Response res) {
		return new ModelAndView(null,"/egresos/proveedor.hbs");
	}
	
	public ModelAndView mostrarProveedor(Request req, Response res) {
		Map<String, Proveedor> model = new HashMap<>();
		String id = req.params("id");
		
		Proveedor proveedor = RepoProveedores.getInstance().buscar(Integer.parseInt(id));
		model.put("proveedor", proveedor);
		return new ModelAndView(model,"/egresos/proveedor.hbs");
	}
	
	private void asignarAtributosA(Proveedor proveedor, Request request){
        if(request.queryParams("nombre") != null){
        	proveedor.setNombreORazonSocial(request.queryParams("nombre"));
        }
        
        if(request.queryParams("dni") != null){
        	proveedor.setDniOCuit(request.queryParams("dni"));
        }
        
        if(request.queryParams("direccion") != null){
        	proveedor.setDireccion(request.queryParams("direccion"));
        }
        
        if(request.queryParams("ciudad") != null){
        	proveedor.setCiudad(request.queryParams("ciudad"));
        }
        
        if(request.queryParams("provincia") != null){
        	proveedor.setProvincia(request.queryParams("provincia"));
        }
        
        if(request.queryParams("pais") != null){
        	proveedor.setPais(request.queryParams("pais"));
        }
    }
	
	public Response crear(Request request, Response response){
		Proveedor proveedor = new Proveedor();
		
		this.asignarAtributosA(proveedor, request);

		withTransaction(() ->{
			RepoProveedores.getInstance().agregarProveedor(proveedor);
		});
		response.redirect("/proveedores");
		return response;
	}	
	
	public Response modificar(Request req, Response res) {
		Proveedor proveedor = RepoProveedores.getInstance().buscar(new Integer(req.params("id")));
        asignarAtributosA(proveedor, req);
        RepoProveedores.getInstance().modificar(proveedor);
        res.redirect("/proveedores");
        return res;
	}
}
