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
import vos.Silo;
import vos.ListaSilos;
import vos.ListaPuertos;
import vos.Puerto;
import vos.Video;
import vos.ListaVideos;


@Path("silos")
public class PuertoAndesSilosServices {

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
	public Response getSilos() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaSilos Silos;
		try {
			Silos = tm.darSilos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Silos).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSiloId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaSilos Silos;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id del Silo no valido");
			Silos = tm.buscarSiloPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Silos).build();
	}
	
 
  
	@PUT
	@Path("/silo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSilo(Silo Silo) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {

			tm.addAreaAlmacenamiento(Silo.getId(), "SILO");
			tm.addSilo(Silo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Silo).build();
	}

	@PUT
	@Path("/silos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSilos(ListaSilos Silos) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addSilos(Silos);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Silos).build();
	}
	

	@POST
	@Path("/silo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSilo(Silo Silo) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updateSilo(Silo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Silo).build();
	}
	
   
	@DELETE
	@Path("/silo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSilo(Silo Silo) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteSilo(Silo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Silo).build();
	}


}
