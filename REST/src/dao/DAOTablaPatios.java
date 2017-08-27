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

import vos.Patio;




public class DAOTablaPatios {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaPatios() {
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


	public ArrayList<Patio> darPatios() throws SQLException, Exception {
		ArrayList<Patio> Patios = new ArrayList<Patio>();

		String sql = "SELECT * FROM PatioS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Patio aux = new Patio(
					rs.getString("ID_ALMACENAMIENTO"),
					rs.getString("ALTO"),
					rs.getString("ANCHO"),
					rs.getString("TIPOCARGA"),rs.getString("ESTADO"));
			Patios.add(aux);
			System.out.println(Patios.size());
		}
		
		return Patios;
	}



	public ArrayList<Patio> buscarPatioPorId(String id) throws SQLException, Exception {
		ArrayList<Patio> Patios = new ArrayList<Patio>();

		String sql = "SELECT * FROM PatioS WHERE ID_ALMACENAMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String ida=rs.getString("ID_ALMACENAMIENTO");
			String alto=rs.getString("ALTO");
			String ancho=rs.getString("ANCHO");
			String tipoCArga=rs.getString("TIPOCARGA");
			String estado = rs.getString("ESTADO");
		
			
			Patios.add(new Patio(ida, alto, ancho, tipoCArga, estado));
		}

		return Patios;
	}


	public void addPatio(Patio Patio) throws SQLException, Exception {

		String sql = "INSERT INTO PatioS VALUES ('";
		sql += Patio.getAlto() + "'" + ",'";
		sql += Patio.getAncho() + "','";
		sql += Patio.getTipoCarga() + "','";
		sql += Patio.getId() + "','";
		sql += Patio.getESTADO() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updatePatio(Patio Patio) throws SQLException, Exception {

		String sql = "UPDATE PatioS SET ";
		sql += "ALTO='" + Patio.getAlto() + "',";
		sql += "ANCHO='" + Patio.getAncho() + "',";
		sql += "TIPOCARGA='" + Patio.getTipoCarga() + "',";
		sql += "ESTADO='" + Patio.getESTADO()+"'";
		sql += " WHERE ID_ALMACENAMIENTO = '" + Patio.getId()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deletePatio(Patio Patio) throws SQLException, Exception {

		String sql = "DELETE FROM PatioS";
		sql += " WHERE ID_ALMACENAMIENTO = '" + Patio.getId()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




}
