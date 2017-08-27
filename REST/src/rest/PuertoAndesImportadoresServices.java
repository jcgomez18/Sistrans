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

import tm.PuertoAndesMaster;
import vos.Importador;
import vos.ListaImportador;
import vos.ListaPuertos;
import vos.Puerto;
@Path("importadores")
public class PuertoAndesImportadoresServices 
{
	
	

		



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
		public Response getImportadores() {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaImportador importadores;
			try {
				importadores = tm.darImportadores();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(importadores).build();
		}


	  
		@GET
		@Path("/id/{id}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getImportadorId(@javax.ws.rs.PathParam("id") String id) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaImportador importadores;
			try {
				if (id == null || id.length() == 0)
					throw new Exception("id del importador no valido");
				importadores = tm.buscarImportadorPorId(id);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(importadores).build();
		}
		
	 
	  
		@PUT
		@Path("/importador")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addImportador(Importador importador) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addImportador(importador);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(importador).build();
		}

		@PUT
		@Path("/importadores")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addImportadores(ListaImportador importadores) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addImportadores(importadores);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(importadores).build();
		}
		

		@POST
		@Path("/importador")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateImportador(Importador importador) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.updateImportador(importador);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(importador).build();
		}
		
	   
		@DELETE
		@Path("/importador")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteImportador(Importador importador) 
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.deleteImportador(importador);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(importador).build();
		}


	}


