/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: BuqueAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una arreglo de Buque
 * @author Juan
 */
public class ListaBuques {
	
	/**
	 * List con los Buques
	 */
	@JsonProperty(value="buques")
	private List<Buque> buques;
	
	/**
	 * Constructor de la clase ListaBuques
	 * @param Buques - Buques para agregar al arreglo de la clase
	 */
	public ListaBuques( @JsonProperty(value="Buques")List<Buque> Buques){
		this.buques = Buques;
	}

	/**
	 * Método que retorna la lista de Buques
	 * @return  List - List con los Buques
	 */
	public List<Buque> getBuques() {
		return buques;
	}

	/**
	 * Método que asigna la lista de Buques que entra como parametro
	 * @param  Buques - List con los Buques ha agregar
	 */
	public void setBuque(List<Buque> Buques) {
		this.buques = Buques;
	}
	
}
