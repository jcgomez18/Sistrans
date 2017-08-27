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
import vos.Exportador;
import vos.ListaExportadores;



@Path("exportadores")
public class PuertoAndesExportadoresServices 
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
		public Response getExportadores() {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaExportadores exportadores;
			try {
				exportadores = tm.darExportadores();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(exportadores).build();
		}


	  
		@GET
		@Path("/id/{id}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getExportadorId(@javax.ws.rs.PathParam("id") String id) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaExportadores exportadores;
			try {
				if (id == null || id.length() == 0)
					throw new Exception("id del exportador no valido");
				exportadores = tm.buscarExportadorPorId(id);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(exportadores).build();
		}
		
	 
	  
		@PUT
		@Path("/exportador")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addExportador(Exportador exportador) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addExportador(exportador);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(exportador).build();
		}

		@PUT
		@Path("/exportadores")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addExportadores(ListaExportadores exportadores) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addExportadores(exportadores);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(exportadores).build();
		}
		

		@POST
		@Path("/exportador")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateExportador(Exportador exportador) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.updateExportador(exportador);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(exportador).build();
		}
		
	   
		@DELETE
		@Path("/exportador")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteexportador(Exportador exportador) 
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.deleteExportador(exportador);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(exportador).build();
		}


	}


