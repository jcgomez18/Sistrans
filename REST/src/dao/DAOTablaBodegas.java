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

import vos.Bodega;




public class DAOTablaBodegas {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaBodegas() {
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


	public ArrayList<Bodega> darBodegas() throws SQLException, Exception {
		ArrayList<Bodega> Bodegas = new ArrayList<Bodega>();

		String sql = "SELECT * FROM BodegaS for update";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Bodega aux = new Bodega
					(rs.getString("ALTO"),					
					rs.getString("ANCHO"),
					rs.getString("PLATAFORMA_EXTERNA"),
					rs.getString("SEPARACION_COLUMNAS"), 
					rs.getString("CUARTO_FRIO"), 
					rs.getString("AREA_CUARTO_FRIO"), 
					rs.getString("ALTO_CUARTOF"), 
					rs.getString("ANCHO_CUARTOF"), 
					rs.getString("AREA_CUARTO"),
					rs.getString("LARGO"),
					rs.getString("LARGO_CUARTOF"),
					rs.getString("ID_ALMACENAMIENTO"),
					rs.getString("ESTADO"));
			Bodegas.add(aux);
			System.out.println(Bodegas.size());
		}
		
		return Bodegas;
	}



	public ArrayList<Bodega> buscarBodegasPorId(String id) throws SQLException, Exception {
		ArrayList<Bodega> Bodegas = new ArrayList<Bodega>();

		String sql = "SELECT * FROM BODEGAS WHERE ID_ALMACENAMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String A=rs.getString("ID_ALMACENAMIENTO");
			String L=rs.getString("LARGO");
			String B=rs.getString("ALTO");
			String T=rs.getString("ANCHO");
			String C=rs.getString("PLATAFORMA_EXTERNA");
			String D=rs.getString("SEPARACION_COLUMNAS");
			String E=rs.getString("CUARTO_FRIO");
			String F=rs.getString("AREA_CUARTO_FRIO");
			String G=rs.getString("ALTO_CUARTOF");
			String H=rs.getString("ANCHO_CUARTOF");
			String I=rs.getString("AREA_CUARTO");
			String y =rs.getString("LARGO_CUARTOF");
			String we =rs.getString("ESTADO");
			
			
			Bodegas.add(new Bodega(B,T,C,D,E,F,G,H,I,L,y,A,we));
		}

		return Bodegas;
	}


	public void addBodega(Bodega Bodega) throws SQLException, Exception {

		//INSERT INTO table SET a=1, b=2, c=3
		String sql = "INSERT INTO BodegaS VALUES('";
		sql += Bodega.getAlto() + "','";
		sql += Bodega.getAncho() + "','";
		sql += Bodega.getPlataforma() + "','";
		sql += Bodega.getSeparacionColumnas() + "','";
		sql += Bodega.getCuartoFrio() + "','";
		sql += Bodega.getAreaCuartoFrio() + "','";
		sql += Bodega.getAltoCF() + "','";	
		sql += Bodega.getAnchoCF() + "','";
		sql += Bodega.getAreaCuartoBodega() + "','";	
		sql += Bodega.getLargo() + "','";	
		sql += Bodega.getLargoCF() + "','";		
		sql += Bodega.getId() + "','";
		sql += Bodega.getEstado()  	+ "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateBodega(Bodega Bodega) throws SQLException, Exception {

		String sql = "UPDATE BodegaS SET ";
		sql += "ALTO='" + Bodega.getAlto() + "',";
		sql += "ANCHO='" + Bodega.getAncho() + "',";
		sql += "PLATAFORMA_EXTERNA='" + Bodega.getPlataforma() + "',";
		sql += "SEPARACION_COLUMNAS='" + Bodega.getSeparacionColumnas() + "',";
		sql += "CUARTO_FRIO='" + Bodega.getCuartoFrio() + "',";
		sql += "AREA_CUARTO_FRIO='" + Bodega.getAreaCuartoFrio() + "',";
		sql += "ALTO_CUARTOF='" + Bodega.getAnchoCF() + "',";
		sql += "ANCHO_CUARTOF='" + Bodega.getAltoCF() + "',";
		sql += "AREA_CUARTO='" + Bodega.getAreaCuartoFrio()+ "',";
		sql += "LARGO='" + Bodega.getLargo() + "',";
		sql += "LARGO_CUARTOF='" + Bodega.getAnchoCF() + "',";	
		sql += "ESTADO='" + Bodega.getEstado()+"'" ;	
		
		
		sql += " WHERE ID_ALMACENAMIENTO = " + Bodega.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteBodega(Bodega Bodega) throws SQLException, Exception {

		String sql = "DELETE FROM BodegaS";
		sql += " WHERE ID_ALMACENAMIENTO = " + Bodega.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




}
