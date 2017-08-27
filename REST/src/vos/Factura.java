package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Factura 
{
	@JsonProperty(value="ID_FACTURA")
	private String id;

	@JsonProperty(value="ID_CARGA")
	private String idCarga;


	@JsonProperty(value="VALOR")
	private int valor;
	
	
	@JsonProperty(value="FECHA_INICIO")
	private Date fechaInicio;
	
	public Factura(@JsonProperty(value="ID_FACTURA")String id,
			@JsonProperty(value="ID_CARGA")String idcarga,
			@JsonProperty(value="VALOR") int valor,
			@JsonProperty(value="FECHA_INICIO") Date fecha
			
			)
			{
				this.id= id; 
				this.idCarga = idcarga;
				this.valor= valor;
				this.fechaInicio = fecha; 
			}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(String idCarga) {
		this.idCarga = idCarga;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	
}
