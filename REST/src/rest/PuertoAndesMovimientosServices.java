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
import vos.Cobertizo;
import vos.ListaCobertizos;
import vos.ListaMovimientos;
import vos.MovimientoCarga;


@Path("movimientos")
public class PuertoAndesMovimientosServices 
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
			ListaMovimientos movs;
			try {
				movs = tm.darMovimientos();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(movs).build();
		}


	  
//		@GET
//		@Path("/id/{id}")
//		@Produces({ MediaType.APPLICATION_JSON })
//		public Response getCobertizoId(@javax.ws.rs.PathParam("id") String id) {
//			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
//			ListaCobertizos Cobertizos;
//			try {
//				if (id == null || id.length() == 0)
//					throw new Exception("id del Cobertizo no valido");
//				Cobertizos = tm.buscarCobertizoPorId(id);
//			} catch (Exception e) {
//				return Response.status(500).entity(doErrorMessage(e)).build();
//			}
//			return Response.status(200).entity(Cobertizos).build();
//		}
//		
	 
	  
		@PUT
		@Path("/movimiento")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addMovimiento(MovimientoCarga mov) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			try {
				tm.addMovimiento(mov);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(mov).build();
		}

		

		@GET
		@Path("/movEspecifico")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getMovimientosEspecifocos(@QueryParam("valor") String valor,
				@QueryParam("tipo") String tipoCarga) {
			PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
			ListaMovimientos movs;
			try {
				movs = tm.darMovimientosEspecificos(valor, tipoCarga);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(movs).build();
		}
	   
	
		
		
		
		
		
		


}
