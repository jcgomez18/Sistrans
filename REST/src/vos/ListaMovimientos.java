package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaMovimientos
{
	@JsonProperty(value="movimientos")
	private List<MovimientoCarga> Mivimientos;
	

	public ListaMovimientos( @JsonProperty(value="movimientos")List<MovimientoCarga> movimientos){
		this.Mivimientos = movimientos;
	}

	
	public List<MovimientoCarga> getMovimientos() {
		return Mivimientos;
	}

	
	public void setMovimientos(List<MovimientoCarga> movimietnos) {
		this.Mivimientos = movimietnos;
	}
}
