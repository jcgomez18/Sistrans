package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Importador;
import vos.Puerto;

public class DAOTablaImportadores
{
	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaImportadores()
	{
		recursos = new ArrayList<Object>();
	}


	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	
	public void setConn(Connection con){
		this.conn = con;
	}


	public ArrayList<Importador> darImportadores() throws SQLException, Exception {
		ArrayList<Importador> x = new ArrayList<Importador>();

		String sql = "SELECT * FROM IMPORTADORES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("ID_IMPORTADOR");
			String nombre = rs.getString("NOMBRE");
			String registro = rs.getString("REGISTRO_ADUANA");
			String habitual = rs.getString("HABITUAL");
			x.add(new Importador(id, nombre, registro, habitual));
		}
		return x;
	}



	public ArrayList<Importador> buscarImportadoresPorId(String id) throws SQLException, Exception {
		ArrayList<Importador> impo = new ArrayList<Importador>();

		String sql = "SELECT * FROM IMPORTADORES WHERE ID_IMPORTADOR ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String id2 = rs.getString("ID_IMPORTADOR");
			String ra = rs.getString("REGISTRO_ADUANA");
			String habitual = rs.getString("HABITUAL");
			impo.add(new Importador(id2, nombre, ra, habitual) );
		}

		return impo;
	}


	public void addImportador(Importador importador) throws SQLException, Exception {

		String sql = "INSERT INTO IMPORTADORES VALUES ('";
		sql += importador.getId() + "'" + ",'";
		sql += importador.getNombre() + "','";
		sql += importador.getRegistro() + "','";
		sql += importador.getHabitual() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateImportador(Importador impo) throws SQLException, Exception {

		String sql = "UPDATE IMPORTADORES SET ";
		sql += "nombre='" + impo.getNombre() + "',";
		sql += "registro_aduana='" + impo.getRegistro() + "',";
		sql += "habitual='" + impo.getHabitual() + "',";
		
		sql += " WHERE ID_IMPORTADOR = " + impo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteImportador(Importador importador) throws SQLException, Exception {

		String sql = "DELETE FROM IMPORTADORES";
		sql += " WHERE id_importador = " + importador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




  

}
