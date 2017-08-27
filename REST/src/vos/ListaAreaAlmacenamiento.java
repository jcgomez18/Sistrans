package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAreaAlmacenamiento 
{
	@JsonProperty(value="ARERAALMACENAMIENTO")
	private List<AreaAlmacenamiento> area;
	

	public ListaAreaAlmacenamiento( @JsonProperty(value="AREAALMACENAMIENTO")List<AreaAlmacenamiento> areas){
		this.area= areas;
	}

	
	public List<AreaAlmacenamiento> getAreas() {
		return area;
	}

	
	public void setAreas(List<AreaAlmacenamiento> areas) {
		this.area = areas;
	}
}
