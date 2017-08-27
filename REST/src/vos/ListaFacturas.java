package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFacturas 
{
	@JsonProperty(value="facturas")
	private List<Factura> facturas;
	

	public ListaFacturas( @JsonProperty(value="facturas")List<Factura> facturas){
		this.facturas = facturas;
	}

	
	public List<Factura> getFacturas() {
		return facturas;
	}

	
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
}
