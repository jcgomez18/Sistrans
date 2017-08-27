/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.AreaAlmacenamiento;
import vos.Bodega;




public class DAOTablaAreasAlmacenamiento {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaAreasAlmacenamiento() {
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


	public ArrayList<AreaAlmacenamiento> darAreasAlmacenamiento() throws SQLException, Exception {
		ArrayList<AreaAlmacenamiento> areas = new ArrayList<AreaAlmacenamiento>();

		String sql = "SELECT * FROM AREASALMACENAMIENTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			AreaAlmacenamiento aux = new AreaAlmacenamiento(rs.getString("ID_AREAALMACENAMIENTO"),rs.getString("TIPO"));
			areas.add(aux);
			System.out.println(areas.size());
		}
		
		return areas;
	}



	public ArrayList<AreaAlmacenamiento> buscarAreasAlmacenamientoPorId(String id) throws SQLException, Exception {
		ArrayList<AreaAlmacenamiento> areas = new ArrayList<AreaAlmacenamiento>();

		String sql = "SELECT * FROM BODEGAS WHERE ID_AREAALMACENAMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String A=rs.getString("ID_AREAALMACENAMIENTO");
			String L=rs.getString("TIPO");
			
			
			
			areas.add(new AreaAlmacenamiento(A,L));
		}

		return areas;
	}

	public void addAreaAlmacenamiento(String id,String tipo) throws SQLException, Exception {

		String sql = "INSERT INTO AREASALMACENAMIENTO VALUES ('";
		sql += id 	+ "','";
		sql += tipo 	+ "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	




}
