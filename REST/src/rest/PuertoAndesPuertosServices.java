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
import vos.ListaPuertos;
import vos.Puerto;
import vos.Video;
import vos.ListaVideos;


@Path("puertos")
public class PuertoAndesPuertosServices {

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
	public Response getPuertos() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaPuertos puertos;
		try {
			puertos = tm.darPuertos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(puertos).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPuertoId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaPuertos puertos;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id del puerto no valido");
			puertos = tm.buscarPuertoPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(puertos).build();
	}
	
 
  
	@PUT
	@Path("/puerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPuerto(Puerto puerto) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addPuerto(puerto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(puerto).build();
	}

	@PUT
	@Path("/puertos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPuertos(ListaPuertos puertos) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addPuertos(puertos);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(puertos).build();
	}
	

	@POST
	@Path("/puerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePuerto(Puerto puerto) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updatePuerto(puerto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(puerto).build();
	}
	
   
	@DELETE
	@Path("/puerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePuerto(Puerto puerto) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deletePuerto(puerto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(puerto).build();
	}


}
