package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Carga;
import vos.Viaje;

public class DAOTablaCargas 
{
	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaCargas()
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


	public ArrayList<Carga> darCargas() throws SQLException, Exception {
		ArrayList<Carga> x = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("ID_CARGA");
			String idBuque = rs.getString("ID_BUQUE");
			String idexpo = rs.getString("ID_EXPORTADOR");
			String numero = rs.getString("NUMERO");
			String tipo = rs.getString("TIPO");
			String volumen = rs.getString("VOLUMENCARGA");
			Date fecha = rs.getDate("FECHA");
			String entregada = rs.getString("ENTREGADA");
			String alma = rs.getString("AREAALMACENAMIENTO");
			Carga t =new Carga(id, idBuque, idexpo, numero, tipo, volumen, fecha, entregada,alma);
			x.add(t);
		} 
		return x;
	}



	public ArrayList<Carga> buscarCargasPorIdBuque(String id) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS WHERE ID_BUQUE ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_CARGA");
			String idBuque = rs.getString("ID_BUQUE");
			String idexpo = rs.getString("ID_EXPORTADOR");
			String numero = rs.getString("NUMERO");
			String tipo = rs.getString("TIPO");
			String volumen = rs.getString("VOLUMENCARGA");
			Date fecha = rs.getDate("FECHA");
			String entregada = rs.getString("ENTREGADA");
			String alma = rs.getString("AREAALMACENAMIENTO");
			
			cargas.add(new Carga(id2, idBuque, idexpo, numero, tipo, volumen, fecha, entregada,alma));
		}

		return cargas;
	}

	public ArrayList<Carga> buscarCargasPorIdAlmacenamiento(String id) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS WHERE AREAALMACENAMIENTO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_CARGA");
			String idBuque = rs.getString("ID_BUQUE");
			String idexpo = rs.getString("ID_EXPORTADOR");
			String numero = rs.getString("NUMERO");
			String tipo = rs.getString("TIPO");
			String volumen = rs.getString("VOLUMENCARGA");
			Date fecha = rs.getDate("FECHA");
			String entregada = rs.getString("ENTREGADA");
			String alma = rs.getString("AREAALMACENAMIENTO");
			
			cargas.add(new Carga(id2, idBuque, idexpo, numero, tipo, volumen, fecha, entregada,alma));
		}

		return cargas;
	}

	


	public void addCarga(Carga carga) throws SQLException, Exception {

		
		
		String sql = "INSERT INTO CARGAS VALUES ('";
		sql += carga.getId() + "'" + ",'";
		sql += carga.getIdBuque() + "','";
		sql += carga.getIdExportador() + "','";
		sql += carga.getNumero() + "','";
		sql += carga.getTipo() + "','";
		sql += carga.getVolumenCarga() + "',";
		
		sql += "TO_DATE('"+carga.getFecha()+"','YYYY-MM-DD')" +",'";
		sql += carga.getEntregada()+ "','";
		sql += carga.getAlma() + "')";

		System.out.println("SQL stmt:" + sql);
		
		//TO_DATE('2012-10-09', 'YYYY-MM-DD')

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	public void insertCarga(Carga carga)throws SQLException, Exception {
		if(carga.getId()!= null)
		{
			
		
		String sql = "UPDATE CARGAS SET   ";
		sql += "FECHA=" + "TO_DATE('"+carga.getFecha()+"','YYYY-MM-DD')," ;
		sql += "ID_BUQUE='',";
		sql += "ENTREGADA='SI',";
		sql += "AREAALMACENAMIENTO='"+ carga.getAlma()+"'";
		
				
		sql += " WHERE NUMERO = '" + carga.getNumero()+"' AND TIPO ='"+ carga.getTipo()+"' AND  VOLUMENCARGA ='"+carga.getVolumenCarga()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		}
	}
	
	
	
	
	public void updateCarga(Carga carga) throws SQLException, Exception {

		String sql = "UPDATE CARGAS SET ";
		sql += "ID_CARGA='" + carga.getId() + "',";
		sql += "ID_BUQUE='" + carga.getIdBuque() + "',";
		sql += "ID_EXPORTADOR='" + carga.getIdExportador() + "',";		
		sql += "NUMERO='" + carga.getNumero() + "',";
		sql += "TIPO='" + carga.getTipo()+ "',";
		sql += "VOLUMENCARGA='" + carga.getVolumenCarga()+ "',";
		sql += "FECHA=" + "TO_DATE('"+carga.getFecha()+"','YYYY-MM-DD')," ;
		sql += "ENTREGADA='" + carga.getEntregada() + "',";
		sql += "AREAALMACENAMIENTO='" + carga.getAlma() + "'";
	
		
		sql += " WHERE ID_CARGA = " + carga.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteCarga(Carga carga) throws SQLException, Exception {

		String sql = "DELETE FROM CARGAS";
		sql += " WHERE ID_CARGA = " + carga.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public ArrayList darTodoExportador(Carga carga) throws SQLException, Exception {
		
		ArrayList cosas = new ArrayList<>();

		String sql = "SELECT * FROM (SELECT *FROM(SELECT ID_EXPORTADOR ,"
				+ "COUNT (ID_EXPORTADOR)AS CANTIDADVIAJES FROM CARGAS WHERE ID_EXPORTADOR = " 
				+ carga.getIdExportador() + " GROUP BY ID_EXPORTADOR) NATURAL JOIN (SELECT * FROM CARGAS))NATURAL JOIN (EXPORTADORES);";


	


		return cosas;
	}

	public ArrayList<Carga> buscarCargasPorId(String id) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS WHERE ID_CARGA ='" + id + "' for update";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_CARGA");
			String idBuque = rs.getString("ID_BUQUE");
			String idexpo = rs.getString("ID_EXPORTADOR");
			String numero = rs.getString("NUMERO");
			String tipo = rs.getString("TIPO");
			String volumen = rs.getString("VOLUMENCARGA");
			Date fecha = rs.getDate("FECHA");
			String entregada = rs.getString("ENTREGADA");
			String alma = rs.getString("AREAALMACENAMIENTO");
			
			cargas.add(new Carga(id2, idBuque, idexpo, numero, tipo, volumen, fecha, entregada,alma));
		}

		return cargas;
	}



}
