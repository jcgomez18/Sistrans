package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaPuertos 
{
	@JsonProperty(value="puertos")
	private List<Puerto> puertos;
	

	public ListaPuertos( @JsonProperty(value="puertos")List<Puerto> puertos){
		this.puertos = puertos;
	}

	
	public List<Puerto> getPuertos() {
		return puertos;
	}

	
	public void setPuertos(List<Puerto> puertos) {
		this.puertos = puertos;
	}
}
