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
import vos.ListaAreaAlmacenamiento;
import vos.ListaBodegas;
import vos.ListaPuertos;
import vos.Puerto;
import vos.Video;
import vos.ListaVideos;


@Path("areaalmacenamiento")
public class PuertoAndesAreaAlmacenamientosServices {

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
	public Response getAreasAlmacenamiento() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaAreaAlmacenamiento areas;
		try {
			areas = tm.darAreasAlmacenamiento();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(areas).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAreaAlmacenamientoPorId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaAreaAlmacenamiento areas;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id del Area de Almacenamiento no valido");
			areas = tm.buscarAreaAlmacenamientoPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(areas).build();
	}
	
 
  
	
	
	

	
	
   
	


}
