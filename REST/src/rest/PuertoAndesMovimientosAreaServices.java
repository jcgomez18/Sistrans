package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.ListaMovimientos;
import vos.ListaMovimientosArea;
import vos.MovimientoAreaAlmacenamiento;
import vos.MovimientoCarga;

@Path("movimientosArea")
public class PuertoAndesMovimientosAreaServices 
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
			public Response getMovimientos() {
				PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
				ListaMovimientosArea movs;
				try {
					movs = tm.darMovimientosAreas();
				} catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
				return Response.status(200).entity(movs).build();
			}


		  
//			@GET
//			@Path("/id/{id}")
//			@Produces({ MediaType.APPLICATION_JSON })
//			public Response getCobertizoId(@javax.ws.rs.PathParam("id") String id) {
//				PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
//				ListaCobertizos Cobertizos;
//				try {
//					if (id == null || id.length() == 0)
//						throw new Exception("id del Cobertizo no valido");
//					Cobertizos = tm.buscarCobertizoPorId(id);
//				} catch (Exception e) {
//					return Response.status(500).entity(doErrorMessage(e)).build();
//				}
//				return Response.status(200).entity(Cobertizos).build();
//			}
//			
		 
		  
			@PUT
			@Path("/movimiento")
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			public Response addMovimiento(MovimientoAreaAlmacenamiento mov) {
				PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
				try {
					tm.addMovimientoArea(mov);
				} catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
				return Response.status(200).entity(mov).build();
			}

			

			@GET
			@Path("/porAreas")
			@Produces({ MediaType.APPLICATION_JSON })
			public Response getMovimientos(@QueryParam("area1") String area1,
					@QueryParam("area2") String area2) {
				PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
				ListaMovimientosArea movs;
				try {
					movs = tm.darMovimientosAreasPorAreas(area1, area2);
				} catch (Exception e) {
					return Response.status(500).entity(doErrorMessage(e)).build();
				}
				return Response.status(200).entity(movs).build();
			}
		   
		
			
			
			
			
			
}
