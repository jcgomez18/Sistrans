package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaExportadores 
{
	@JsonProperty(value="EXPORTADORES")
	private List<Exportador> exportadores;
	

	public ListaExportadores( @JsonProperty(value="EXPORTADORES")List<Exportador> exportadores){
		this.exportadores = exportadores;
	}

	
	public List<Exportador> getExportadores() {
		return exportadores;
	}

	
	public void setExportador(List<Exportador> exportadores) {
		this.exportadores = exportadores;
	}
}
