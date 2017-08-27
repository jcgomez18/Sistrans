package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Exportador;


public class DAOTablaExportadores
{
	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaExportadores()
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


	public ArrayList<Exportador> darExportadores() throws SQLException, Exception {
		ArrayList<Exportador> x = new ArrayList<Exportador>();

		String sql = "SELECT * FROM EXPORTADORES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("ID_exportador");
			String nombre = rs.getString("NOMBRE");
			String rut = rs.getString("RUT");
		
			x.add(new Exportador(id, nombre, rut));
		}
		return x;
	}



	public ArrayList<Exportador> buscarExportadoresPorId(String id) throws SQLException, Exception {
		ArrayList<Exportador> impo = new ArrayList<Exportador>();

		String sql = "SELECT * FROM exportadorES WHERE ID_exportador ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String id2 = rs.getString("ID_exportador");
			String ra = rs.getString("RUT");

			impo.add(new Exportador(id2, nombre, ra) );
		}

		return impo;
	}


	public void addExportador(Exportador exportador) throws SQLException, Exception {

		String sql = "INSERT INTO exportadorES VALUES ('";
		sql += exportador.getId() + "'" + ",'";
		sql += exportador.getNombre() + "','";
	
		sql += exportador.getRut() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateExportador(Exportador impo) throws SQLException, Exception {

		String sql = "UPDATE exportadorES SET ";
		sql += "nombre='" + impo.getNombre() + "',";
		sql += "RUT='" + impo.getRut() + "',";
		
		
		sql += " WHERE ID_exportador = " + impo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteExportador(Exportador exportador) throws SQLException, Exception {

		String sql = "DELETE FROM exportadorES";
		sql += " WHERE id_EXPORTADOR = " + exportador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	



  

}
