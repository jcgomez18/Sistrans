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


public class Carga {

	//// Atributos
	
	@JsonProperty(value="ID_CARGA")
	private String id;
	
	@JsonProperty(value="ID_BUQUE")
	private String idBuque;


	
	@JsonProperty(value="ID_EXPORTADOR")
	private String idExportador;
	
	
	@JsonProperty(value="NUMERO")
	private String numero;
	
	

	@JsonProperty(value="TIPO")
	private String tipo;
	
	@JsonProperty(value="VOLUMENCARGA")
	private String volumenCarga;
	
	@JsonProperty(value="FECHA")
	private Date fecha;
	
	@JsonProperty(value="ENTREGADA")
	private String entregada;
	
	
	@JsonProperty(value="ID_ALMACENAMIENTO")
	private String ID_ALMACENAMIENTO;
	
	
	


	public Carga(
			@JsonProperty(value="ID_CARGA")String id,
			@JsonProperty(value="ID_BUQUE")String idBuque,
			@JsonProperty(value="ID_EXPORTADOR") String idExportador,
			@JsonProperty(value="NUMERO") String numero,
			@JsonProperty(value="TIPO") String tipo,
			@JsonProperty(value="VOLUMENCARGA") String volumenCarga,
			@JsonProperty(value="FECHA") Date fecha,
			@JsonProperty(value="ENTREGADA") String enreg,
			@JsonProperty(value="ID_ALMACENAMIENTO") String alma
		
			
			)
	{	
		super();
		
		this.id = id ;
		this. idBuque = idBuque;
		this.idExportador = idExportador;
		this.numero = numero;
		this.tipo = tipo;
		this.volumenCarga = volumenCarga;
		this.fecha= fecha; 
		this.entregada=enreg; 
		this.ID_ALMACENAMIENTO = alma;
	}






	public String getEntregada() {
		return entregada;
	}






	public void setEntregada(String entregada) {
		this.entregada = entregada;
	}






	public String getAlma() {
		return ID_ALMACENAMIENTO;
	}






	public void setAlma(String alma) {
		this.ID_ALMACENAMIENTO = alma;
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getIdBuque() {
		return idBuque;
	}






	public void setIdBuque(String idBuque) {
		this.idBuque = idBuque;
	}






	public String getIdExportador() {
		return idExportador;
	}






	public void setIdExportador(String idExportador) {
		this.idExportador = idExportador;
	}






	public String getNumero() {
		return numero;
	}






	public void setNumero(String numero) {
		this.numero = numero;
	}






	public String getTipo() {
		return tipo;
	}






	public void setTipo(String tipo) {
		this.tipo = tipo;
	}






	public String getVolumenCarga() {
		return volumenCarga;
	}






	public void setVolumenCarga(String volumenCarga) {
		this.volumenCarga = volumenCarga;
	}






	public Date getFecha() {
		return fecha;
	}






	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}






	

}




