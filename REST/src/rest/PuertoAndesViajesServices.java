package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.Buque;
import vos.ListaBuques;
import vos.ListaViajes;
import vos.Viaje;

@Path("viajes")
public class PuertoAndesViajesServices 
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
		public Response getViajes() {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaViajes viajes;
			try {
				viajes = tm.darViajes();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}


	  
		@GET
		@Path("/id/{id}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getViajeId(@javax.ws.rs.PathParam("id") String id) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaViajes viajes;
			try {
				if (id == null || id.length() == 0)
					throw new Exception("id del viaje no valido");
				viajes = tm.buscarViajePorId(id);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}
		
	 
	  
		@PUT
		@Path("/viaje")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addViaje(Viaje viaje) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addViaje(viaje);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viaje).build();
		}

		@PUT
		@Path("/viajes")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addViajes(ListaViajes viajes) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addViajes(viajes);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}
		

		@POST
		@Path("/viaje")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateViaje(Viaje viaje) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.updateViaje(viaje);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viaje).build();
		}
		
	   
		@DELETE
		@Path("/viaje")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteViaje(Viaje viaje) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.deleteViaje(viaje);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viaje).build();
		}
		
		
		
		@GET
		@Path("/salen")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getViajesQueSalen(@QueryParam("nombre") String nombreBuque,
				@QueryParam("tipo") String tipoBuque,
				@QueryParam("fechaI") String fechaInicial,
				@QueryParam("fechaF") String fechaFinal) 
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaViajes viajes;
			try {
				viajes = tm.darViajesQueSalenConInfroIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}

	
		@GET
		@Path("/entran")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getViajesQueEntran(@QueryParam("nombre") String nombreBuque,
				@QueryParam("tipo") String tipoBuque,
				@QueryParam("fechaI") String fechaInicial,
				@QueryParam("fechaF") String fechaFinal) 
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaViajes viajes;
			try {
				viajes = tm.darViajesQueEntranConInfroIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}
		@GET
		@Path("/saleNoInfo")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getViajesQueSalenNOInfo(@QueryParam("nombre") String nombreBuque,
				@QueryParam("tipo") String tipoBuque,
				@QueryParam("fechaI") String fechaInicial,
				@QueryParam("fechaF") String fechaFinal) 
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaViajes viajes;
			try {
				viajes = tm.darViajesQueSalenConNOInfroIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}
		
		@GET
		@Path("/entranNo")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getViajesQueEntranNoInfo(@QueryParam("nombre") String nombreBuque,
				@QueryParam("tipo") String tipoBuque,
				@QueryParam("fechaI") String fechaInicial,
				@QueryParam("fechaF") String fechaFinal) 
		{
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaViajes viajes;
			try {
				viajes = tm.darViajesQueEntranConNoInfroIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(viajes).build();
		}

	}


