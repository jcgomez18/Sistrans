package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaViajes 
{
	@JsonProperty(value="viajes")
	private List<Viaje> viajes;
	

	public ListaViajes( @JsonProperty(value="viajes")List<Viaje> viajes){
		this.viajes = viajes;
	}

	
	public List<Viaje> getViajes() {
		return viajes;
	}

	
	public void setViaje(List<Viaje> viajes) {
		this.viajes = viajes;
	}
	
}
