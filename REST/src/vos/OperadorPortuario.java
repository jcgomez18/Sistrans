package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class OperadorPortuario 
{
	
		
			
			@JsonProperty(value="ID_OPERADOR")
			private String id;

			@JsonProperty(value="NOMBRE")
			private String nombre;
			
			@JsonProperty(value="CODIGO")
			private String codigo;
		
			
			@JsonProperty(value="ADMINISTRADOR")
			private String admin;
			
			
			public OperadorPortuario(
					@JsonProperty(value="id")String id,
					@JsonProperty(value="NOMBRE")String name,
					@JsonProperty(value="CODIGO") String cod,
					@JsonProperty(value="ADMINISTRADOR") String admi
					) 
			{
				super();
				this.id = id;
				this.nombre = name;
				this.codigo = cod;
				this.admin = admi;
			}
			

			public String getAdmin() {
				return admin;
			}


			public void setAdmin(String admin) {
				this.admin = admin;
			}


			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getNombre() {
				return nombre;
			}

			public void setNombre(String nombre) {
				this.nombre = nombre;
			}

			public String getCodigo() {
				return codigo;
			}

			public void setCodigo(String codigo) {
				this.codigo = codigo;
			}
			
			
}
