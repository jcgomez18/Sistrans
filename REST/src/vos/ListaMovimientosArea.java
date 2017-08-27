package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaMovimientosArea
{

	
		@JsonProperty(value="movimientos")
		private List<MovimientoAreaAlmacenamiento> Mivimientos;
		

		public ListaMovimientosArea( @JsonProperty(value="movimientos")List<MovimientoAreaAlmacenamiento> movimientos){
			this.Mivimientos = movimientos;
		}

		
		public List<MovimientoAreaAlmacenamiento> getMovimientos() {
			return Mivimientos;
		}

		
		public void setMovimientos(List<MovimientoAreaAlmacenamiento> movimietnos) {
			this.Mivimientos = movimietnos;
		}
	}

