package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.RepoEntidades.RepoEntidades;
import dds.gesoc.model.RepoEntidades.RepositoriosEntidadesH;
import dds.gesoc.model.egresos.Egreso;
import dds.gesoc.model.egresos.RepoEgresos;
import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;
import dds.gesoc.model.RepoEntidades.RepoCategorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerEntidades implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView listar(Request req, Response res){
		Map<String, List<Entidad>> model = new HashMap<>();
		List<Entidad> entidades = RepositoriosEntidadesH.getInstance().listar();
		
		model.put("entidades", entidades);
		return new ModelAndView(model, "entidades/entidades.hbs");
	}
	
	public ModelAndView mostrar(Request req, Response res){
		Map<String, Entidad> model = new HashMap<>();
		String id = req.params("id");
		
		Entidad entidad = RepositoriosEntidadesH.getInstance().buscar(Long.parseLong(id));
		model.put("entidad", entidad);
		return new ModelAndView(model, "entidades/show.hbs");
	}
	
	public ModelAndView nuevo(Request req, Response res){
		return new ModelAndView(null, "entidades/entidadesnew.hbs");
	}
	
	private void asignarAtributosA(Entidad entidad, Request request){
        if(request.queryParams("nombreFicticio") != null){
            entidad.setNombreFicticio(request.queryParams("nombreFicticio"));
        }
        
        if(request.queryParams("montoEsperado") != null){
            double montoEsperado = new Double(request.queryParams("montoEsperado"));
            entidad.setMontoEsperado(montoEsperado);
        }
        
        if(request.queryParams("categoria") != null){
            Categoria unaCategoria = RepoCategorias.getInstance().buscar(new Integer(request.queryParams("rol")));
            entidad.setCategoria(unaCategoria);
        }
        
        if(entidad.getClass().isAssignableFrom(EntidadBase.class)) {
        	if(request.queryParams("descripcion") != null){
                ((EntidadBase) entidad).setDescripcion(request.queryParams("descripcion"));
            }
        }
        
        if(entidad.getClass().isAssignableFrom(EntidadJuridica.class)) {
        	if(request.queryParams("razonSocial") != null){
        		((EntidadJuridica) entidad).setRazonSocial(request.queryParams("razonSocial"));
            }
        	
        	if(request.queryParams("cuit") != null){
        		((EntidadJuridica) entidad).setCuit(request.queryParams("cuit"));
            }
        	
        	if(request.queryParams("direccionPostal") != null){
        		((EntidadJuridica) entidad).setDireccionPostal(request.queryParams("razonSocial"));
            }
        	
        	if(request.queryParams("codigoInscripcionIGJ") != null){
                Integer codigoInscripcionIGJ = new Integer(request.queryParams("codigoInscripcionIGJ"));
                ((EntidadJuridica) entidad).setCodigoInscripcionIGJ(codigoInscripcionIGJ);
            }
        }
    }
	
	public Void crear(Request req, Response res){
		Entidad entidadNueva = new Entidad();
		this.asignarAtributosA(entidadNueva, req);
		withTransaction(() ->{
			RepositoriosEntidadesH.getInstance().agregar(entidadNueva);
		});
		res.redirect("/entidades");
		return null;
	}
}
