package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaEquipoApoyo 
{
	@JsonProperty(value="EQUIPOS_APOYO")
	private List<EquipoApoyo> equiposApoyos;
	

	public ListaEquipoApoyo( @JsonProperty(value="EQUIPOS_APOYO")List<EquipoApoyo> equiposApoyos){
		this.equiposApoyos = equiposApoyos;
	}

	
	public List<EquipoApoyo> getequiposApoyos() {
		return equiposApoyos;
	}

	
	public void setequiposApoyo(List<EquipoApoyo> equiposApoyos) {
		this.equiposApoyos = equiposApoyos;
	}
}
