package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaOperadorPortuario 
{
	@JsonProperty(value="OPERADORES_PORTUARIO")
	private List<OperadorPortuario> operadorPortuarios;
	

	public ListaOperadorPortuario( @JsonProperty(value="OPERADORES_PORTUARIO")List<OperadorPortuario> operadorPortuarios){
		this.operadorPortuarios = operadorPortuarios;
	}

	
	public List<OperadorPortuario> getoperadorPortuarios() {
		return operadorPortuarios;
	}

	
	public void setoperadorPortuario(List<OperadorPortuario> operadorPortuarios) {
		this.operadorPortuarios = operadorPortuarios;
	}
}
