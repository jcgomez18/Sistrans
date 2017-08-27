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
import vos.AreaAlmacenamiento;
import vos.Buque;
import vos.Carga;
import vos.ListaCarga;
import vos.ListaViajes;
import vos.Viaje;



@Path("cargas")
public class PuertoAndesCargasServices 
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
	public Response getCargas() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaCarga cargas;
		try {
			cargas = tm.darCargas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargas).build();
	}



	
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCargaIdBuque(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaCarga cargas;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id de la carga no valido");
			cargas = tm.buscarCargaPorIdBuque(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargas).build();
	}
 

	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCargaId(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaCarga cargas;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id de la carga no valido");
			cargas = tm.buscarCargaPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargas).build();
	}
 


	
  
	@PUT
	@Path("/carga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCarga(Carga carga) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addCarga(carga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}

	@PUT
	@Path("/cargas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCargas(ListaCarga cargas) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addCargas(cargas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargas).build();
	}
	

	@POST
	@Path("/carga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response entregarCarga(Carga carga) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.entregarCarga(carga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}
	
	@POST
	@Path("/carga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCarga(Carga carga) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updateCarga(carga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}
	
   
	@DELETE
	@Path("/carga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCarga(Carga carga) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteCarga(carga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}
	
	@POST
	@Path("/idDestino/{idDestino}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cargarBuque(@javax.ws.rs.PathParam("idDestino") String id,Carga carga) 
	{
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.cargarBuque(id, carga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}
	
	@POST
	@Path("/descargarBuque")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response descargarBuque(Carga carga) 
	{
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.descargarBuque( carga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}
	
	
	@POST
	@Path("/desabilitarArea")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deshabilitarArea(AreaAlmacenamiento area) 
	{
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deshabilitarArea(area);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(area).build();
	}
	
	
	@POST
	@Path("/desabilitarBuque")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response desabilitarBuque(Buque x) 
	{
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deshabilitarBuque(x);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(x).build();
	}
	
	

}
