
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class ListaBodegas {
	
	@JsonProperty(value="bodegas")
	private List<Bodega> bodegas;
	

	public ListaBodegas( @JsonProperty(value="bodegas")List<Bodega> bodegas){
		this.bodegas = bodegas;
	}

	
	public List<Bodega> getBodegas() {
		return bodegas;
	}

	
	public void setBodega(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}
	
}
