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

import vos.Silo;




public class DAOTablaSilos {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaSilos() {
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


	public ArrayList<Silo> darSilos() throws SQLException, Exception {
		ArrayList<Silo> Silos = new ArrayList<Silo>();

		String sql = "SELECT * FROM SiloS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Silo aux = new Silo(
					rs.getString("ID_ALMACENAMIENTO"),
					rs.getString("NOMBRE"),
					rs.getString("CAPACIDAD"), rs.getString("ESTADO"));
			Silos.add(aux);
			System.out.println(Silos.size());
		}
		
		return Silos;
	}



	public ArrayList<Silo> buscarSiloPorId(String id) throws SQLException, Exception {
		ArrayList<Silo> Silos = new ArrayList<Silo>();

		String sql = "SELECT * FROM SiloS WHERE ID_ALMACENAMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String ida=rs.getString("ID_ALMACENAMIENTO");
			String nombre=rs.getString("NOMBRE");
			String capacidad=rs.getString("CAPACIDAD");
			String estado = rs.getString("ESTADO");
		
			
			Silos.add(new Silo(ida, nombre,  capacidad, estado));
		}

		return Silos;
	}


	public void addSilo(Silo Silo) throws SQLException, Exception {

		String sql = "INSERT INTO SiloS VALUES ('";
		sql += Silo.getNombre() + "'" + ",'";
		sql += Silo.getCapacidad() + "','";
		sql += Silo.getId() + "','";
		sql += Silo.getESTADO() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateSilo(Silo Silo) throws SQLException, Exception {

		String sql = "UPDATE SiloS SET ";
		sql += "NOMBRE='" + Silo.getNombre() + "',";
		sql += "CAPACIDAD='" + Silo.getCapacidad() + "',";
		sql += "ESTADO='" + Silo.getESTADO()+"'";
		sql += "WHERE ID_ALMACENAMIENTO = " + Silo.getId()+"'";
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteSilo(Silo Silo) throws SQLException, Exception {

		String sql = "DELETE FROM SiloS";
		sql += " WHERE ID_ALMACENAMIENTO = '" + Silo.getId()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




}
