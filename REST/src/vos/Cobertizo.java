package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cobertizo 
{
	
		
		@JsonProperty(value="ID_ALMACENAMIENTO")
		private String id;

		@JsonProperty(value="ALTO")
		private String alto;
		
		@JsonProperty(value="ANCHO")
		private String ancho;
		
		@JsonProperty(value="TIPOCARGA")
		private String tipoCarga;
		
		@JsonProperty(value="ESTADO")
		private String ESTADO;
		
		
		
		
		
		public Cobertizo(@JsonProperty(value="id")String id, @JsonProperty(value="ALTO")String alto,
				@JsonProperty(value="ANCHO") String ancho, @JsonProperty(value="TIPOCARGA") String tc, @JsonProperty(value="ESTADO")
		String ESTADO) {
			super();
			this.id = id;
			this.alto  = alto;
			this.ancho = ancho; 
			this.tipoCarga = tc; 
			this.ESTADO = ESTADO; 
			
		}


	


		public String getESTADO() {
			return ESTADO;
		}





		public void setESTADO(String ESTADO) {
			this.ESTADO = ESTADO;
		}





		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getAlto() {
			return alto;
		}


		public void setAlto(String alto) {
			this.alto = alto;
		}


		public String getAncho() {
			return ancho;
		}


		public void setAncho(String ancho) {
			this.ancho = ancho;
		}


		public String getTipoCarga() {
			return tipoCarga;
		}


		public void setTipoCarga(String tipoCarga) {
			this.tipoCarga = tipoCarga;
		}
		
		
		
}
