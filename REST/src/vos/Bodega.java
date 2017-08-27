package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Bodega 
{
	
	
	@JsonProperty(value="ID_ALMACENAMIENTO")
	private String id;

	@JsonProperty(value="LARGO")
	private String largo;
	
	@JsonProperty(value="ALTO")
	private String alto;
	
	@JsonProperty(value="ANCHO")
	private String ancho;
	
	@JsonProperty(value="PLATAFORMA_EXTERNA")
	private String plataforma;
	
	@JsonProperty(value="SEPARACION_COLUMNAS")
	private String separacionColumnas;

	
	@JsonProperty(value="CUARTO_FRIO")
	private String cuartoFrio;
	
	@JsonProperty(value="AREA_CUARTO_FRIO")
	private String areaCuartoFrio;
	
	@JsonProperty(value="ALTO_CUARTOF")
	private String altoCF;
	
	@JsonProperty(value="ANCHO_CUARTOF")
	private String anchoCF;
	
	@JsonProperty(value="LARGO_CUARTOF")
	private String largoCF;
		
	@JsonProperty(value="AREA_CUARTO")
	private String areaCuartoBodega;
	

	@JsonProperty(value="ESTADO")
	private String estado;
	
	
	
	
	public Bodega(@JsonProperty(value="ALTO")String alto,
			@JsonProperty(value="ANCHO") String ancho,			
			@JsonProperty(value="PLATAFORMA_EXTERNA")String plataforma,
			@JsonProperty(value="SEPARACION_COLUMNAS")String separa,
			@JsonProperty(value="CUARTO_FRIO")String cf,
			@JsonProperty(value="AREA_CUARTO_FRIO")String acf,
			@JsonProperty(value="ALTO_CUARTOF")String altocf,
			@JsonProperty(value="ANCHO_CUARTOF")String anchocf,
			@JsonProperty(value="AREA_CUARTO")String areacuartoBodega,
			@JsonProperty(value="LARGO")String largo,
			@JsonProperty(value="LARGO_CUARTOF")String largocf,
			@JsonProperty(value="ID_ALMACENAMIENTO")String id,
			@JsonProperty(value="ESTADO")String estado
			
			) {
		super();
		this.id = id;
		this.alto = alto;
		this.largo = largo;
		this.ancho = ancho;
		this.plataforma = plataforma;
		this.separacionColumnas = separa;
		this.cuartoFrio = cf;
		this.areaCuartoFrio = acf;
		this.altoCF = altocf;
		this.anchoCF = anchocf;
		this.largoCF= largocf;
		this.areaCuartoBodega = areacuartoBodega; 
		this.estado=estado;
		
		
	}
	
	

	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getLargo() {
		return largo;
	}

	public void setLargo(String largo) {
		this.largo = largo;
	}

	public String getLargoCF() {
		return largoCF;
	}

	public void setLargoCF(String largoCF) {
		this.largoCF = largoCF;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlto() {
		return alto;
	}

	public void setAlto(String alto) {
		this.alto = alto;
	}

	public String getAncho() {
		return ancho;
	}

	public void setAncho(String ancho) {
		this.ancho = ancho;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getSeparacionColumnas() {
		return separacionColumnas;
	}

	public void setSeparacionColumnas(String separacionColumnas) {
		this.separacionColumnas = separacionColumnas;
	}

	public String getCuartoFrio() {
		return cuartoFrio;
	}

	public void setCuartoFrio(String cuartoFrio) {
		this.cuartoFrio = cuartoFrio;
	}

	public String getAreaCuartoFrio() {
		return areaCuartoFrio;
	}

	public void setAreaCuartoFrio(String areaCuartoFrio) {
		this.areaCuartoFrio = areaCuartoFrio;
	}

	public String getAltoCF() {
		return altoCF;
	}

	public void setAltoCF(String altoCF) {
		this.altoCF = altoCF;
	}

	public String getAnchoCF() {
		return anchoCF;
	}

	public void setAnchoCF(String anchoCF) {
		this.anchoCF = anchoCF;
	}

	public String getAreaCuartoBodega() {
		return areaCuartoBodega;
	}

	public void setAreaCuartoBodega(String areaCuartoBodega) {
		this.areaCuartoBodega = areaCuartoBodega;
	}
	
	
	
}
