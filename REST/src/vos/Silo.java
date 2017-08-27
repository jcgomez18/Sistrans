/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import org.codehaus.jackson.annotate.*;


public class Silo {

	//// Atributos


	@JsonProperty(value="ID_ALMACENAMIENTO")
	private String id;

	@JsonProperty(value="NOMBRE")
	private String nombre;


	@JsonProperty(value="CAPACIDAD")
	private String capacidad;
	
	@JsonProperty(value="ESTADO")
	private String ESTADO;	

	


	public Silo(@JsonProperty(value="ID_ALMACENAMIENTO")String id,
			@JsonProperty(value="NOMBRE")String nombre,
			@JsonProperty(value="CAPACIDAD") String capacidad, @JsonProperty(value="ESTADO") String estado)
			
	{
		super();
		
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.ESTADO = estado;
		
	}






	public String getESTADO() {
		return ESTADO;
	}






	public void setESTADO(String eSTADO) {
		ESTADO = eSTADO;
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getNombre() {
		return nombre;
	}






	public void setNombre(String nombre) {
		this.nombre = nombre;
	}






	public String getCapacidad() {
		return capacidad;
	}






	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}


}