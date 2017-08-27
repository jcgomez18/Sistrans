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

import tm.PuertoAndesMaster;
import vos.Patio;
import vos.ListaPatios;
import vos.ListaPuertos;
import vos.Puerto;
import vos.Video;
import vos.ListaVideos;


@Path("patios")
public class PuertoAndesPatiosServices {

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
	public Response getPatios() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaPatios Patios;
		try {
			Patios = tm.darPatios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Patios).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPatioId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaPatios Patios;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id del Patio no valido");
			Patios = tm.buscarPatioPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Patios).build();
	}
	
 
  
	@PUT
	@Path("/patio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPatio(Patio Patio) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {

			tm.addAreaAlmacenamiento(Patio.getId(), "PATIO");
			tm.addPatio(Patio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Patio).build();
	}

	@PUT
	@Path("/patios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPatios(ListaPatios Patios) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addPatios(Patios);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Patios).build();
	}
	

	@POST
	@Path("/patio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePatio(Patio Patio) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updatePatio(Patio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Patio).build();
	}
	
   
	@DELETE
	@Path("/patio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePatio(Patio Patio) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deletePatio(Patio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Patio).build();
	}


}
