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
import vos.Carga;
import vos.Factura;
import vos.ListaCarga;
import vos.ListaFacturas;


@Path("facturas")
public class PuertoAndesFacturasServices 
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
	public Response getFacturas() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaFacturas facturas;
		try {
			facturas = tm.darFacturas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(facturas).build();
	}


  
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFacturaIdCarga(@javax.ws.rs.PathParam("id") String id) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaFacturas facturas;
		try {
			if (id == null || id.length() == 0)
				throw new Exception("id de la carga no valido");
			facturas = tm.buscarFacturaPorIdCarga(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(facturas).build();
	}
	
 
  
	@PUT
	@Path("/factura")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFactura(Factura factura) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addFactura(factura);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(factura).build();
	}

	

	
   
	@DELETE
	@Path("/carga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFactura(Factura factura) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteFactura(factura);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(factura).build();
	}

}
