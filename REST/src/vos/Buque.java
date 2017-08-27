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


public class Buque {

	//// Atributos


	@JsonProperty(value="ID_BUQUE")
	private String id;

	@JsonProperty(value="NOMBRE")
	private String nombre;


	@JsonProperty(value="TIPO")
	private String tipo;
	
	
	@JsonProperty(value="CAPACIDAD")
	private String capacidad;
	
	
	@JsonProperty(value="NOMBRE_AGENTE_MARITIMO")
	private String nombreAgente;
	
	
	@JsonProperty(value="REGISTRO_CAPITANIA")
	private String registro;
	
	@JsonProperty(value="ESTADO")
	private String estado;
	
	
	
	


	public Buque(@JsonProperty(value="ID_BUQUE")String id,
			@JsonProperty(value="NOMBRE")String nombre,
			@JsonProperty(value="TIPO") String tipo,
			@JsonProperty(value="CAPACIDAD") String capacidad,
			@JsonProperty(value="NOMBRE_AGENTE_MARITIMO") String nombreAgente,
			@JsonProperty(value="REGISTRO_CAPITANIA") String registro,
			@JsonProperty(value="ESTADO") String estado
			
			)
	{	
		super();
		
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.nombreAgente = nombreAgente;
		this.registro = registro;
		this.estado=estado;
	}






	public String getEstado() {
		return estado;
	}






	public void setEstado(String estado) {
		this.estado = estado;
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






	public String getNombreAgente() {
		return nombreAgente;
	}






	public void setNombreAgente(String nombreAgente) {
		this.nombreAgente = nombreAgente;
	}






	public String getRegistro() {
		return registro;
	}






	public void setRegistro(String registro) {
		this.registro = registro;
	}
	

	


}
