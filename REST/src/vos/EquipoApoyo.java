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


public class EquipoApoyo {

	//// Atributos


	@JsonProperty(value="ID_EQUIPO_APOYO")
	private String id;

	@JsonProperty(value="TIPO")
	private String tipo;


	@JsonProperty(value="CAPACIDAD_TONELADAS")
	private String capacidad;
	
	
	@JsonProperty(value="CANTIDAD")
	private String cantidad;
	
	
	


	public EquipoApoyo(@JsonProperty(value="ID_EQUIPO_APOYO")String id,
			@JsonProperty(value="TIPO")String tipo,
			@JsonProperty(value="CAPACIDAD_TONELADAS") String capacidad,
			@JsonProperty(value="CANTIDAD") String cantidad )
	{	
		super();
		
		this.id = id;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.cantidad = cantidad;
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





	public String getCapacidad() {
		return capacidad;
	}





	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}





	public String getCantidad() {
		return cantidad;
	}





	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	


}
