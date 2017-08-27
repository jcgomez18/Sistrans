package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaPatios 
{
	@JsonProperty(value="PATIOS")
	private List<Patio> patios;
	

	public ListaPatios( @JsonProperty(value="PATIOS")List<Patio> patios){
		this.patios = patios;
	}

	
	public List<Patio> getPatios() {
		return patios;
	}

	
	public void setPatio(List<Patio> patios) {
		this.patios = patios;
	}
}
