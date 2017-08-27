package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Factura;
import vos.Patio;

public class DAOTablaFacturas 
{
	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaFacturas() {
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


	public ArrayList<Factura> darFacturas() throws SQLException, Exception {
		ArrayList<Factura> facturas = new ArrayList<Factura>();

		String sql = "SELECT * FROM FACTURAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next())
		{
			
			Factura aux = new Factura(
					rs.getString("ID_FACTURA"),
					rs.getString("ID_CARGA"),
					rs.getInt("VALOR"),
					rs.getDate("FECHA_INICIO"));
			facturas.add(aux);
			
		}
		
		return facturas;
	}



	public ArrayList<Factura> buscarFacturaPorCarga(String id) throws SQLException, Exception {
		ArrayList<Factura> facturas = new ArrayList<Factura>();

		String sql = "SELECT * FROM FACTURAS WHERE ID_CARGA ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String idi =rs.getString("ID_FACTURA");
			String  idcarga =rs.getString("ID_CARGA");
			int valor=rs.getInt("VALOR");
			Date fecha =rs.getDate("FECHA_INICIO");
		
			
			facturas.add(new Factura(idi, idcarga, valor, fecha));
		}

		return facturas;
	}


	public void addFactura(Factura factura) throws SQLException, Exception {

		java.sql.Date x = java.sql.Date.valueOf( factura.getFechaInicio().toString() );
		java.util.Date fechaActual = new java.util.Date(); 
		double valornuevo = (( fechaActual.getTime() - x.getTime())* 0.0000000115)*100;
		String sql = "INSERT INTO Facturas VALUES ('";
		sql += factura.getId() + "'" + ",";
		sql += "TO_DATE('"+factura.getFechaInicio()+"','YYYY-MM-DD')" +",";
		sql += valornuevo + ",'";
		sql += factura.getIdCarga() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	

	
	
	public void deleteFactura(Factura factura) throws SQLException, Exception {

		String sql = "DELETE FROM FACTURAS";
		sql += " WHERE ID_FACTURA = " + factura.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}



}
