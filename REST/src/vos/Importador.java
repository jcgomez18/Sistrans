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


public class Importador {

	//// Atributos


	@JsonProperty(value="ID_IMPORTADOR")
	private String id;

	@JsonProperty(value="NOMBRE")
	private String nombre;


	@JsonProperty(value="REGISTRO_ADUANA")
	private String registro;
	
	
	@JsonProperty(value="HABITUAL")
	private String habitual;
	
	
	


	public Importador(@JsonProperty(value="ID_IMPORTADOR")String id,
			@JsonProperty(value="NOMBRE")String nombre,
			@JsonProperty(value="REGISTRO_ADUANA") String registro,
			@JsonProperty(value="HABITUAL") String habitual )
	{	
		super();
		
		this.id = id;
		this.nombre = nombre;
		this.registro = registro;
		this.habitual = habitual;
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





	public String getRegistro() {
		return registro;
	}





	public void setRegistro(String registro) {
		this.registro = registro;
	}





	public String getHabitual() {
		return habitual;
	}





	public void setHabitual(String habitual) {
		this.habitual = habitual;
	}




}