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


public class Exportador {

	//// Atributos


	@JsonProperty(value="ID_EXPORTADOR")
	private String id;

	@JsonProperty(value="NOMBRE")
	private String nombre;


	@JsonProperty(value="RUT")
	private String rut;

	private ListaCarga cargas;
	
	
	


	public Exportador(@JsonProperty(value="ID_EXPORTADOR")String id,
			@JsonProperty(value="NOMBRE")String nombre,
			@JsonProperty(value="RUT") String rut)
	{	
		super();
		
		this.id = id;
		this.nombre = nombre;
		this.rut = rut;
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






	public String getRut() {
		return rut;
	}






	public void setRut(String rut) {
		this.rut = rut;
	}


}