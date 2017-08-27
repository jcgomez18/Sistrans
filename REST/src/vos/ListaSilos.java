package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSilos 
{
	@JsonProperty(value="SILOS")
	private List<Silo> silos;
	

	public ListaSilos( @JsonProperty(value="SILOS")List<Silo> silos){
		this.silos = silos;
	}

	
	public List<Silo> getSilos() {
		return silos;
	}

	
	public void setSilo(List<Silo> silos) {
		this.silos = silos;
	}
}
