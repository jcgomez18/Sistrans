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

import vos.Buque;




public class DAOTablaBuques {


	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaBuques() {
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


	public ArrayList<Buque> darBuques() throws SQLException, Exception {
		ArrayList<Buque> buques = new ArrayList<Buque>();

		String sql = "SELECT * FROM buqueS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Buque aux = new Buque(rs.getString("ID_buque"),
					rs.getString("TIPO"),rs.getString("CAPACIDAD"),
					rs.getString("NOMBRE"), 
					rs.getString("NOMBRE_AGENTE_MARITIMO"), 
					rs.getString("REGISTRO_CAPITANIA"),
					rs.getString("ESTADO")
					);
			buques.add(aux);
			System.out.println(buques.size());
		}
		
		return buques;
	}



	public ArrayList<Buque> buscarBuquesPorId(String id) throws SQLException, Exception {
		ArrayList<Buque> buques = new ArrayList<Buque>();

		String sql = "SELECT * FROM buqueS WHERE ID_buque ='" + id + "' for update";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String ida=rs.getString("ID_buque");
			String tipo=rs.getString("TIPO");
			String capacidad=rs.getString("CAPACIDAD");
			String nombre=rs.getString("NOMBRE");
			String nombreAgente=rs.getString("NOMBRE_AGENTE_MARITIMO");
			String registroCapitania=rs.getString("REGISTRO_CAPITANIA");
			String estado=rs.getString("ESTADO");
			
			buques.add(new Buque(ida,nombre, tipo, capacidad,nombreAgente,registroCapitania,estado));
		}

		return buques;
	}


	public void addBuque(Buque buque) throws SQLException, Exception {

		String sql = "INSERT INTO buqueS VALUES ('";
		sql += buque.getId() + "'" + ",'";
		sql += buque.getTipo() + "','";
		sql += buque.getCapacidad() + "','";
		sql += buque.getNombre() + "','";
		sql += buque.getNombreAgente() + "','";
		sql += buque.getRegistro() +"','";
		sql += buque.getEstado() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateBuque(Buque buque) throws SQLException, Exception {

		String sql = "UPDATE buqueS SET ";
		sql += "REGISTRO_CAPITANIA='" + buque.getRegistro() + "',";
		sql += "tipo='" + buque.getTipo() + "',";
		sql += "capacidad='" + buque.getCapacidad() + "',";
		sql += "nombre='" + buque.getNombre() + "',";
		sql += "estado='" + buque.getEstado() + "',";
		sql += "NOMBRE_AGENTE_MARITIMO='" + buque.getNombreAgente();
		sql += "' WHERE id_buque = " + buque.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteBuque(Buque buque) throws SQLException, Exception {

		String sql = "DELETE FROM buqueS";
		sql += " WHERE id_buque = " + buque.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}




}
