package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCarga 
{
	@JsonProperty(value="cargas")
	private List<Carga> cargas;
	

	public ListaCarga( @JsonProperty(value="cargas")List<Carga> cargas){
		this.cargas = cargas;
	}

	
	public List<Carga> getcargas() {
		return cargas;
	}

	
	public void setcarga(List<Carga> cargas) {
		this.cargas = cargas;
	}
}
