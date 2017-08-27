/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package rest;


import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import dao.DAOTablaCobertizos;
import tm.PuertoAndesMaster;
import vos.Cobertizo;
import vos.ListaCobertizos;
import vos.ListaPuertos;
import vos.Puerto;
import vos.Video;
import vos.ListaVideos;


@Path("cobertizos")
public class PuertoAndesCobertizosServices {

	// Servicios REST tipo GET:



	@Context
	private ServletContext context;


	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCobertizos() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaCobertizos Cobertizos;
		try {
			Cobertizos = tm.darCobertizos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Cobertizos).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCobertizoId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaCobertizos Cobertizos;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id del Cobertizo no valido");
			Cobertizos = tm.buscarCobertizoPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Cobertizos).build();
	}
	
 
  
	@PUT
	@Path("/cobertizo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCobertizo(Cobertizo Cobertizo) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addAreaAlmacenamiento(Cobertizo.getId(), "COBERTIZO");
			tm.addCobertizo(Cobertizo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Cobertizo).build();
	}

	@PUT
	@Path("/cobertizos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCobertizos(ListaCobertizos Cobertizos) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addCobertizos(Cobertizos);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Cobertizos).build();
	}
	

	@POST
	@Path("/cobertizo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCobertizo(Cobertizo Cobertizo) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updateCobertizo(Cobertizo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Cobertizo).build();
	}
	
   
	@DELETE
	@Path("/cobertizo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCobertizo(Cobertizo Cobertizo) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteCobertizo(Cobertizo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Cobertizo).build();
	}

	
	
	
	
	
	



}
