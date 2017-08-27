package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class MovimientoCarga 
{
	@JsonProperty(value="ID_MOVIMIENTO")
	private String ID_MOVIMIENTO;

	@JsonProperty(value="ID_CARGA")
	private String ID_CARGA;


	@JsonProperty(value="ORIGEN")
	private String ORIGEN;
	
	@JsonProperty(value="DESTINO")
	private String DESTINO;
	
	
	@JsonProperty(value="FECHAMOVIMIENTO")
	private Date FECHAMOVIMIENTO;
	
	public MovimientoCarga(@JsonProperty(value="ID_MOVIMIENTO")String id,
			@JsonProperty(value="ID_CARGA")String carga,
			@JsonProperty(value="ORIGEN")String origen,
			@JsonProperty(value="DESTINO")String destino,
			@JsonProperty(value="FECHAMOVIMIENTO")Date fecha)
			{
			
			   this.ID_MOVIMIENTO = id;
				this.ID_CARGA = carga;
				this.ORIGEN = origen; 
				this.DESTINO = destino; 
				this.FECHAMOVIMIENTO = fecha; 		
	}

	public String getID_MOVIMIENTO() {
		return ID_MOVIMIENTO;
	}

	public void setID_MOVIMIENTO(String iD_MOVIMIENTO) {
		ID_MOVIMIENTO = iD_MOVIMIENTO;
	}

	public String getID_CARGA() {
		return ID_CARGA;
	}

	public void setID_CARGA(String iD_CARGA) {
		ID_CARGA = iD_CARGA;
	}

	public String getORIGEN() {
		return ORIGEN;
	}

	public void setORIGEN(String oRIGEN) {
		ORIGEN = oRIGEN;
	}

	public String getDESTINO() {
		return DESTINO;
	}

	public void setDESTINO(String dESTINO) {
		DESTINO = dESTINO;
	}

	public Date getFECHAMOVIMIENTO() {
		return FECHAMOVIMIENTO;
	}

	public void setFECHAMOVIMIENTO(Date fECHAMOVIMIENTO) {
		FECHAMOVIMIENTO = fECHAMOVIMIENTO;
	}
	
	
}
