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
import vos.Bodega;
import vos.ListaBodegas;
import vos.ListaPuertos;
import vos.Puerto;
import vos.Video;
import vos.ListaVideos;


@Path("bodegas")
public class PuertoAndesBodegasServices {

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
	public Response getBodegas() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaBodegas Bodegas;
		try {
			Bodegas = tm.darBodegas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Bodegas).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBodegaId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaBodegas Bodegas;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id del bodega no valido");
			Bodegas = tm.buscarBodegaPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Bodegas).build();
	}
	
 
  
	@PUT
	@Path("/bodega")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBodega(Bodega Bodega) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addAreaAlmacenamiento(Bodega.getId(), "BODEGA");
			tm.addBodega(Bodega);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Bodega).build();
	}

	@PUT
	@Path("/bodegas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBodegas(ListaBodegas Bodegas) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addBodegas(Bodegas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Bodegas).build();
	}
	

	@POST
	@Path("/bodega")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBodega(Bodega Bodega) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updateBodega(Bodega);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Bodega).build();
	}
	
   
	@DELETE
	@Path("/bodega")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBodega(Bodega Bodega) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteBodega(Bodega);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Bodega).build();
	}


}
