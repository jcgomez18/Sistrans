package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaImportador 
{

	@JsonProperty(value="IMPORTADORES")
	private List<Importador> importadores;
	

	public ListaImportador( @JsonProperty(value="IMPORTADORES")List<Importador> importadores){
		this.importadores = importadores;
	}

	
	public List<Importador> getimportadores() {
		return importadores;
	}

	
	public void setimportador(List<Importador> importadores) {
		this.importadores = importadores;
}
	}
