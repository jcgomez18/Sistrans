
package vos;

import org.codehaus.jackson.annotate.*;


public class Puerto {

	//// Atributos


	@JsonProperty(value="ID_PUERTO")
	private String ID_PUERTO;

	@JsonProperty(value="NOMBRE")
	private String NOMBRE;


	@JsonProperty(value="PAIS")
	private String PAIS;
	
	
	@JsonProperty(value="CIUDAD")
	private String CIUDAD;
	
	
	


	public Puerto(@JsonProperty(value="ID_PUERTO")String idas, @JsonProperty(value="NOMBRE")String nombre,@JsonProperty(value="PAIS") String pais,@JsonProperty(value="CIUDAD") String ciudad )
	{	
		super();
		
		this.ID_PUERTO = idas;
		this.NOMBRE = nombre;
		this.PAIS = pais;
		this.CIUDAD = ciudad;
	}





	public String getId() {
		return ID_PUERTO;
	}





	public void setId(String iD_PUERTO) {
		ID_PUERTO = iD_PUERTO;
	}





	public String getNombre() {
		return NOMBRE;
	}





	public void setNombre(String nOMBRE) {
		NOMBRE = nOMBRE;
	}





	public String getPais() {
		return PAIS;
	}





	public void setPais(String pAIS) {
		PAIS = pAIS;
	}





	public String getCiudad() {
		return CIUDAD;
	}





	public void setCiudad(String cIUDAD) {
		CIUDAD = cIUDAD;
	}
	
}
