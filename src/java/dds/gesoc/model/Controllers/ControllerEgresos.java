package dds.gesoc.model.Controllers;

import java.util.*;

import dds.gesoc.model.egresos.DatosEgreso;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.Proveedor;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.egresos.RepoProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerEgresos {
	public static ModelAndView listar(Request req, Response res) {
		Map<String, List<Egreso>> modelo = new HashMap<>();
		List<Egreso> egresos = RepoEgresos.getInstance().getEgresos();
		modelo.put("egresos", egresos);
		return new ModelAndView(modelo,"/egresos/egresos.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res) {
		return new ModelAndView(null,"/egresos/egresosnew.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res) {
		Map<String,Object> modelo = new HashMap<>();
		String id = req.params("id");
		Egreso egreso = RepoEgresos.getInstance().buscar(Long.parseLong(id));
		List<Proveedor> proveedores = RepoProveedores.getInstance().getProveedores();
		modelo.put("proveedores", proveedores);
		modelo.put("egreso", egreso);
		return new ModelAndView(modelo,"/egresos/egreso.hbs");
	}
	
	//TODO: Ver los DatosEgreso
	public static ModelAndView crear(Request req, Response res) {
		Map<String, Proveedor> modelo = new HashMap<>();
		String id = req.params("id");
		//DatosEgreso datosEgresoNuevo = new DatosEgreso(req.queryParams(RepoProveedor.getInstance().buscar(<id>).getNombreORazonSocial().toString()), documento, medioPago);
		Proveedor proveedor = RepoProveedores.getInstance().buscar(Long.parseLong(id));
		modelo.put("proveedor",proveedor);
		return new ModelAndView(null,"/egresos/new.hbs");
	}
	
	public static ModelAndView modificar(Request req, Response res) {
		return new ModelAndView(null,"/egresos/new.hbs");
	}
	
	public static ModelAndView editar(Request req, Response res) {
		return new ModelAndView(null,"/egresos/new.hbs");
	}
}
