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


public class AreaAlmacenamiento {

	//// Atributos


	@JsonProperty(value="ID_ALMACENAMIENTO")
	private String id;

	@JsonProperty(value="TIPO")
	private String tipo;



	


	public AreaAlmacenamiento(@JsonProperty(value="ID_ALMACENAMIENTO")String id,
			@JsonProperty(value="TIPO")String tipo)
			
	{
		super();
		
		this.id = id;
		this.tipo = tipo;
		
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getTipo() {
		return tipo;
	}






	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}



