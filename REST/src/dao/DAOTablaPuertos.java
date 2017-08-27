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

import vos.Puerto;
import vos.Video;


public class DAOTablaPuertos {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaPuertos() {
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


	public ArrayList<Puerto> darPuertos() throws SQLException, Exception {
		ArrayList<Puerto> puertos = new ArrayList<Puerto>();

		String sql = "SELECT * FROM PUERTOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Puerto aux = new Puerto(rs.getString("ID_PUERTO"), rs.getString("NOMBRE"),rs.getString("PAIS"), rs.getString("CIUDAD"));
			puertos.add(aux);
			
		}
		
		return puertos;
	}



	public ArrayList<Puerto> buscarPuertosPorId(String id) throws SQLException, Exception {
		ArrayList<Puerto> puertos = new ArrayList<Puerto>();

		String sql = "SELECT * FROM PUERTOS WHERE ID_PUERTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String id2 = rs.getString("ID_PUERTO");
			String pais = rs.getString("PAIS");
			String ciudad = rs.getString("CIUDAD");
			puertos.add(new Puerto(id2, nombre, pais, ciudad));
		}

		return puertos;
	}


	public void addPuerto(Puerto puerto) throws SQLException, Exception {

		String sql = "INSERT INTO PUERTOS VALUES ('";
		sql += puerto.getId() + "'" + ",'";
		sql += puerto.getNombre() + "','";
		sql += puerto.getPais() + "','";
		sql += puerto.getCiudad() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updatePuerto(Puerto puerto) throws SQLException, Exception {

		String sql = "UPDATE PUERTOS SET ";
		sql += "nombre='" + puerto.getNombre() + "',";
		sql += "pais='" + puerto.getPais() + "',";
		sql += "ciudad=" + puerto.getCiudad();
		sql += " WHERE id_PUERTO = " + puerto.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deletePuerto(Puerto puerto) throws SQLException, Exception {

		String sql = "DELETE FROM PUERTOS";
		sql += " WHERE id_puerto = " + puerto.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




}
