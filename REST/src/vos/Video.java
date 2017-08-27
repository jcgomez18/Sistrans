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


public class Video {

	//// Atributos

	/**
	 * Id del video
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre del video
	 */
	@JsonProperty(value="name")
	private String name;

	/**
	 * Duración en minutos del video
	 */
	@JsonProperty(value="duration")
	private int duration;

	/**
	 * Método constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parámetro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duración en minutos del video.
	 */
	public Video(@JsonProperty(value="id")int id, @JsonProperty(value="name")String name,@JsonProperty(value="duration") int duration) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
	}
	
	/**
	 * Método getter del atributo duration
	 * @return duración del video en minutos
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Método setter del atributo duration <b>post: </b> La duración del video
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param duration - Duración en minutos del video.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Método getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param id - Id del video
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo name
	 * @return nombre del video
	 */
	public String getName() {
		return name;
	}

	/**
	 * Método setter del atributo name <b>post: </b> El nombre del video ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param name - Id del video
	 */
	public void setName(String name) {
		this.name = name;
	}


}
