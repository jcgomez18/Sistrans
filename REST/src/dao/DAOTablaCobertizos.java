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

import vos.Cobertizo;




public class DAOTablaCobertizos {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaCobertizos() {
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


	public ArrayList<Cobertizo> darCobertizos() throws SQLException, Exception {
		ArrayList<Cobertizo> Cobertizos = new ArrayList<Cobertizo>();

		String sql = "SELECT * FROM CobertizoS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Cobertizo aux = new Cobertizo(
					rs.getString("ID_ALMACENAMIENTO"),
					rs.getString("ALTO"),
					rs.getString("ANCHO"),
					rs.getString("TIPOCARGA"),
					rs.getString("ESTADO"));
			Cobertizos.add(aux);
			System.out.println(Cobertizos.size());
		}
		
		return Cobertizos;
	}



	public ArrayList<Cobertizo> buscarCobertizoPorId(String id) throws SQLException, Exception {
		ArrayList<Cobertizo> Cobertizos = new ArrayList<Cobertizo>();

		String sql = "SELECT * FROM CobertizoS WHERE ID_ALMACENAMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String ida=rs.getString("ID_ALMACENAMIENTO");
			String alto=rs.getString("ALTO");
			String ancho=rs.getString("ANCHO");
			String tipoCArga=rs.getString("TIPOCARGA");
			String estado=rs.getString("ESTADO");
		
			
			Cobertizos.add(new Cobertizo(ida, alto, ancho, tipoCArga,estado));
		}

		return Cobertizos;
	}


	public void addCobertizo(Cobertizo Cobertizo) throws SQLException, Exception {

		String sql = "INSERT INTO CobertizoS VALUES ('";
		sql += Cobertizo.getAlto() + "'" + ",'";
		sql += Cobertizo.getAncho() + "','";
		sql += Cobertizo.getTipoCarga() + "','";
		sql += Cobertizo.getId()  + "','";
		sql += Cobertizo.getESTADO() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateCobertizo(Cobertizo Cobertizo) throws SQLException, Exception {

		String sql = "UPDATE CobertizoS SET ";
		sql += "ALTO='" + Cobertizo.getAlto() + "',";
		
		sql += "ANCHO='" + Cobertizo.getAncho() + "',";
		sql += "TIPOCARGA='" + Cobertizo.getTipoCarga() + "',";
		sql += "ESTADO='" + Cobertizo.getESTADO()+"'";
		
		sql += " WHERE ID_ALMACENAMIENTO = '" + Cobertizo.getId()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteCobertizo(Cobertizo Cobertizo) throws SQLException, Exception {

		String sql = "DELETE FROM CobertizoS";
		sql += " WHERE ID_ALMACENAMIENTO = " + Cobertizo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




}
