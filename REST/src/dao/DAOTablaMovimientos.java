package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cobertizo;
import vos.MovimientoCarga;
import vos.Viaje;

public class DAOTablaMovimientos
{
	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaMovimientos() {
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


	public ArrayList<MovimientoCarga> darMovimientos() throws SQLException, Exception {
		ArrayList<MovimientoCarga> movimientos = new ArrayList<MovimientoCarga>();

		String sql = "SELECT * FROM MOVIMIENTOS_CARGA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			MovimientoCarga aux = new MovimientoCarga(
					rs.getString("ID_MOVIMIENTO"),
					rs.getString("ID_CARGA"),
					rs.getString("ORIGEN"),
					rs.getString("DESTINO"),
					rs.getDate("FECHAMOVIMIENTO"));
			movimientos.add(aux);
			System.out.println(movimientos.size());
		}
		
		return movimientos;
	}



	public ArrayList<MovimientoCarga> buscarMovimientoPorId(String id) throws SQLException, Exception {
		ArrayList<MovimientoCarga> movimientos = new ArrayList<MovimientoCarga>();

		String sql = "SELECT * FROM MOVIMIENTOS_CARGA WHERE ID_MOVIMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			MovimientoCarga aux = new MovimientoCarga(
					rs.getString("ID_MOVIMIENTO"),
					rs.getString("ID_CARGA"),
					rs.getString("ORIGEN"),
					rs.getString("DESTINO"),
					rs.getDate("FECHAMOVIMIENTO"));
			movimientos.add(aux);
			System.out.println(movimientos.size());
		}

		return movimientos;
	}


	public void addMovimiento(MovimientoCarga mov) throws SQLException, Exception {

		System.out.println(mov.getID_MOVIMIENTO());
		System.out.println(mov.getID_CARGA());
		System.out.println(mov.getORIGEN());
		System.out.println(mov.getDESTINO());
		System.out.println(mov.getFECHAMOVIMIENTO());
		String sql = "INSERT INTO MOVIMIENTOS_CARGA VALUES ('";
		sql += mov.getID_MOVIMIENTO() + "'" + ",'";
		sql += mov.getID_CARGA() + "','";
		sql += mov.getORIGEN() + "','";
		sql += mov.getDESTINO()  + "',";
		sql += "TO_DATE('"+mov.getFECHAMOVIMIENTO()+"','YYYY-MM-DD')" + ")";

		//INSERT INTO MOVIMIENTOS_CARGA VALUES ('3','1','Sapo','Sapo',TO_DATE('2010-10-09','YYYY-MM-DD'))
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	


	public ArrayList<MovimientoCarga> buscarMovimientoEspecifico(String valor, String tipoCarga) throws SQLException, Exception {
		ArrayList<MovimientoCarga> movimientos = new ArrayList<MovimientoCarga>();
		
		double time = System.currentTimeMillis();
		double timefinal = 0;
		String sql = "SELECT  MOVIMIENTOS_CARGA.ID_CARGA, MOVIMIENTOS_CARGA.ID_MOVIMIENTO, MOVIMIENTOS_CARGA.ORIGEN, MOVIMIENTOS_CARGA.DESTINO, MOVIMIENTOS_CARGA.FECHAMOVIMIENTO FROM MOVIMIENTOS_CARGA, FACTURAS , CARGAS WHERE  FACTURAS.ID_CARGA   = CARGAS.ID_CARGA AND MOVIMIENTOS_CARGA.ID_CARGA = CARGAS.ID_CARGA AND MOVIMIENTOS_CARGA.ID_CARGA = CARGAS.ID_CARGA AND FACTURAS.VALOR   >"+valor +" AND CARGAS.TIPO  = '"+ tipoCarga+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		timefinal = System.currentTimeMillis()-time;
		while (rs.next())
		{
			
			MovimientoCarga aux = new MovimientoCarga(
					rs.getString("ID_MOVIMIENTO"),
					rs.getString("ID_CARGA"),
					rs.getString("ORIGEN"),
					rs.getString("DESTINO"),
					rs.getDate("FECHAMOVIMIENTO"));
			movimientos.add(aux);
			System.out.println(movimientos.size());
		}
		System.out.println("Tiempo Consulta:"+ timefinal + "Milisegundos");
		return movimientos;
	}


	
	


	
	
	public void deleteMovimiento(MovimientoCarga muevelo) throws SQLException, Exception {

		String sql = "DELETE FROM MOVIMIENTOS_CARGA";
		sql += " WHERE ID_MOVIMIENTO = " + muevelo.getID_MOVIMIENTO();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


}
