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



import java.sql.Date;

import org.codehaus.jackson.annotate.*;




public class Viaje {

	//// Atributos


	@JsonProperty(value="ID_VIAJE")
	private String id;

	@JsonProperty(value="PUERTO_ORIGEN")
	private String puertoOrigen;


	@JsonProperty(value="HORA_SALIDA")
	private String horaSalida;
	
	
	@JsonProperty(value="FECHA_SALIDA")
	private Date fechaSalida;
	
	
	@JsonProperty(value="PUERTO_DESTINO")
	private String puertoDestino;
	
	
	@JsonProperty(value="HORA_LLEGADA")
	private String horaLlegada;
	
	@JsonProperty(value="FECHA_LLEGADA")
	private Date fechaLLegada;
	
	
	@JsonProperty(value="ID_BUQUE")
	private String idBuque;
	
	
	
	


	public Viaje(@JsonProperty(value="ID_VIAJE")String id,
			@JsonProperty(value="PUERTO_ORIGEN")String puertoOrigen,
			@JsonProperty(value="HORA_SALIDA") String horaSalida,
			@JsonProperty(value="FECHA_SALIDA") Date fechaSalida,
			@JsonProperty(value="PUERTO_DESTINO") String puertoDestino,
			@JsonProperty(value="HORA_LLEGADA") String horaLlegada,
			@JsonProperty(value="FECHA_LLEGADA") Date fechaLLegada,
			@JsonProperty(value="ID_BUQUE") String idBuque
			)
	{	
		super();
		
		this.id = id;
		this.puertoOrigen = puertoOrigen;
		this.horaSalida = horaSalida;
		this.fechaSalida = fechaSalida;
		this.puertoDestino = puertoDestino;
		this.horaLlegada = horaLlegada;
		this.fechaLLegada = fechaLLegada;
		this.idBuque = idBuque;
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getPuertoOrigen() {
		return puertoOrigen;
	}






	public void setPuertoOrigen(String puertoOrigen) {
		this.puertoOrigen = puertoOrigen;
	}






	public String getHoraSalida() {
		return horaSalida;
	}






	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}






	public Date getFechaSalida() {
		return fechaSalida;
	}






	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}






	public String getPuertoDestino() {
		return puertoDestino;
	}






	public void setPuertoDestino(String puertoDestino) {
		this.puertoDestino = puertoDestino;
	}






	public String getHoraLlegada() {
		return horaLlegada;
	}






	public void setHoraLlegada(String horaLlegada) {
		this.horaLlegada = horaLlegada;
	}






	public Date getFechaLLegada() {
		return fechaLLegada;
	}






	public void setFechaLLegada(Date fechaLLegada) {
		this.fechaLLegada = fechaLLegada;
	}






	public String getIdBuque() {
		return idBuque;
	}






	public void setIdBuque(String idBuque) {
		this.idBuque = idBuque;
	}






}
