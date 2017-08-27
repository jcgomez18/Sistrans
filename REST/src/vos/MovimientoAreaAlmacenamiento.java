package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class MovimientoAreaAlmacenamiento
{

       @JsonProperty(value="ID_AREA")
		private String ID_AREA;

		@JsonProperty(value="TIPO_AREA")
		private String TIPO_AREA;


		@JsonProperty(value="CARGA")
		private String CARGA;
		
		@JsonProperty(value="TIPO_MOVIMIENTO")
		private String TIPO_MOVIMIENTO;
		
		
		@JsonProperty(value="FECHA")
		private Date FECHA;
		
		public MovimientoAreaAlmacenamiento(@JsonProperty(value="ID_AREA")String idcarga,
				@JsonProperty(value="TIPO_AREA")String tipoarea,
				@JsonProperty(value="CARGA")String carga,
				@JsonProperty(value="TIPO_MOVIMIENTO")String tipomov,
				@JsonProperty(value="FECHA")Date fecha)
				{
				
				   this.ID_AREA = idcarga;
					this.TIPO_AREA = tipoarea;
					this.CARGA = carga; 
					this.TIPO_MOVIMIENTO = tipomov; 
					this.FECHA = fecha; 		
		}

		public String getID_AREA() {
			return ID_AREA;
		}

		public void setID_AREA(String iD_AREA) {
			ID_AREA = iD_AREA;
		}

		public String getTIPO_AREA() {
			return TIPO_AREA;
		}

		public void setTIPO_AREA(String tIPO_AREA) {
			TIPO_AREA = tIPO_AREA;
		}

		public String getCARGA() {
			return CARGA;
		}

		public void setCARGA(String cARGA) {
			CARGA = cARGA;
		}

		public String getTIPO_MOVIMIENTO() {
			return TIPO_MOVIMIENTO;
		}

		public void setTIPO_MOVIMIENTO(String tIPO_MOVIMIENTO) {
			TIPO_MOVIMIENTO = tIPO_MOVIMIENTO;
		}

		public Date getFECHA() {
			return FECHA;
		}

		public void setFECHA(Date fECHA) {
			FECHA = fECHA;
		}
		
		

}
