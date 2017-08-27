package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MovimientoAreaAlmacenamiento;
import vos.MovimientoCarga;

public class DAOTablaMovimientosArea
{
	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaMovimientosArea() {
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


	public ArrayList<MovimientoAreaAlmacenamiento> darMovimientos() throws SQLException, Exception {
		ArrayList<MovimientoAreaAlmacenamiento> movimientos = new ArrayList<MovimientoAreaAlmacenamiento>();

		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO_MOV";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			MovimientoAreaAlmacenamiento aux = new MovimientoAreaAlmacenamiento(
					rs.getString("ID_AREA"),
					rs.getString("TIPO_AREA"),
					rs.getString("CARGA"),
					rs.getString("TIPO_MOVIMIENTO"),
					rs.getDate("FECHA"));
			movimientos.add(aux);
			System.out.println(movimientos.size());
		}
		
		return movimientos;
	}



	public ArrayList<MovimientoAreaAlmacenamiento> buscarMovimientoPorCarga(String idCarga) throws SQLException, Exception {
		ArrayList<MovimientoAreaAlmacenamiento> movimientos = new ArrayList<MovimientoAreaAlmacenamiento>();

		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO_MOV WHERE CARGA ='" + idCarga + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			MovimientoAreaAlmacenamiento aux = new MovimientoAreaAlmacenamiento(
					rs.getString("ID_AREA"),
					rs.getString("TIPO_AREA"),
					rs.getString("CARGA"),
					rs.getString("TIPO_MOVIMIENTO"),
					rs.getDate("FECHA"));
			movimientos.add(aux);
			System.out.println(movimientos.size());
		}

		return movimientos;
	}


	public void addMovimiento(MovimientoAreaAlmacenamiento mov) throws SQLException, Exception {

		
		String sql = "INSERT INTO AREAS_ALMACENAMIENTO_MOV VALUES ('";
		sql += mov.getID_AREA() + "'" + ",'";
		sql += mov.getTIPO_AREA() + "','";
		sql += mov.getCARGA() + "','";
		sql += mov.getTIPO_MOVIMIENTO()  + "',";
		sql += "TO_DATE('"+mov.getFECHA()+"','YYYY-MM-DD')" + ")";

		//INSERT INTO MOVIMIENTOS_CARGA VALUES ('3','1','Sapo','Sapo',TO_DATE('2010-10-09','YYYY-MM-DD'))
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	
	public ArrayList<MovimientoAreaAlmacenamiento> buscarMovimientoPorAreas(String idArea1, String idArea2) throws SQLException, Exception {
		ArrayList<MovimientoAreaAlmacenamiento> movimientos = new ArrayList<MovimientoAreaAlmacenamiento>();
		double time = System.currentTimeMillis();
		double timefinal = 0;
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO_MOV where ID_AREA="+idArea1+"or ID_AREA="+idArea2;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		timefinal = System.currentTimeMillis()-time;
		while (rs.next())
		{
			MovimientoAreaAlmacenamiento aux = new MovimientoAreaAlmacenamiento(
					rs.getString("ID_AREA"),
					rs.getString("TIPO_AREA"),
					rs.getString("CARGA"),
					rs.getString("TIPO_MOVIMIENTO"),
					rs.getDate("FECHA"));
			movimientos.add(aux);
			System.out.println(movimientos.size());
		}
		System.out.println("Tiempo Consulta:"+ timefinal + "Milisegundos");
		return movimientos;
	}

	


	
	
//	public void deleteMovimiento(MovimientoAreaAlmacenamiento muevelo) throws SQLException, Exception {
//
//		String sql = "DELETE FROM AREAS_ALMACENAMIENTO_MOV";
//		sql += " WHERE ID_MOVIMIENTO = " + muevelo.getID_MOVIMIENTO();
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}


}
