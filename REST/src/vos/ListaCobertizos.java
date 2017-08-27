package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCobertizos 
{
	@JsonProperty(value="cobertizos")
	private List<Cobertizo> cobertizos;
	

	public ListaCobertizos( @JsonProperty(value="cobertizos")List<Cobertizo> cobertizos){
		this.cobertizos = cobertizos;
	}

	
	public List<Cobertizo> getCobertizos() {
		return cobertizos;
	}

	
	public void setCobertizo(List<Cobertizo> cobertizos) {
		this.cobertizos = cobertizos;
	}
}
