package dds.gesoc.model.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dds.gesoc.model.organizaciones.Categoria;
import dds.gesoc.model.organizaciones.Entidad;
import dds.gesoc.model.organizaciones.EntidadBase;
import dds.gesoc.model.organizaciones.EntidadJuridica;
import dds.gesoc.model.repositorios.RepoCategorias;
import dds.gesoc.model.repositorios.RepoEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ControllerEntidades implements WithGlobalEntityManager, TransactionalOps{
	
	public ModelAndView listar(Request req, Response res){
		Map<String, List<Entidad>> model = new HashMap<>();
		List<Entidad> entidades = RepoEntidades.getInstance().listar();
		
		model.put("entidades", entidades);
		return new ModelAndView(model, "/entidades/entidades.hbs");
	}
	
	public ModelAndView mostrar(Request req, Response res){
		Map<String, Entidad> model = new HashMap<>();
		String id = req.params("id");
		
		Entidad entidad = RepoEntidades.getInstance().buscar(Integer.parseInt(id));
		model.put("entidad", entidad);
		return new ModelAndView(model, "/entidades/show.hbs");
	}
	
	public ModelAndView nuevo(Request req, Response res){
		List<Categoria> categorias = RepoCategorias.getInstance().listar();
        Map<String, List<Categoria>> model = new HashMap<>();
        model.put("categorias", categorias);
		return new ModelAndView(model, "/entidades/new.hbs");
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
            Categoria unaCategoria = RepoCategorias.getInstance().buscar(new Integer(request.queryParams("categoria")));
            entidad.setCategoria(unaCategoria);
        }
    }
	
	public Response crearEntidadBase(Request request, Response response){
		EntidadBase entidadBaseNueva = new EntidadBase();
		this.asignarAtributosA(entidadBaseNueva, request);
		if(request.queryParams("descripcion") != null){
                entidadBaseNueva.setDescripcion(request.queryParams("descripcion"));
        } 
		withTransaction(() ->{
			RepoEntidades.getInstance().agregar(entidadBaseNueva);
		});
		response.redirect("/entidades");
		return response;
	}
	
	public Response crearEntidadJuridica(Request request, Response response){
		EntidadJuridica entidadJuridicaNueva = new EntidadJuridica();
		this.asignarAtributosA(entidadJuridicaNueva, request);
		
		if(request.queryParams("razonSocial") != null){
        	entidadJuridicaNueva.setRazonSocial(request.queryParams("razonSocial"));
        }	
        if(request.queryParams("cuit") != null){
        	entidadJuridicaNueva.setCuit(request.queryParams("cuit"));
        }
        	
        if(request.queryParams("direccionPostal") != null){
        	entidadJuridicaNueva.setDireccionPostal(request.queryParams("razonSocial"));
        }
        	
        if(request.queryParams("codigoInscripcionIGJ") != null){
        	Integer codigoInscripcionIGJ = new Integer(request.queryParams("codigoInscripcionIGJ"));
            entidadJuridicaNueva.setCodigoInscripcionIGJ(codigoInscripcionIGJ);
        }
        
		withTransaction(() ->{
			RepoEntidades.getInstance().agregar(entidadJuridicaNueva);
		});
		response.redirect("/entidades");
		return response;
	}
}
