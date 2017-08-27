package dao;

import java.sql.Connection;



import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.rmi.CORBA.Tie;

import vos.Buque;
import vos.Carga;
import vos.Importador;
import vos.Viaje;

public class DAOTablaViajes 
{

	private ArrayList<Object> recursos;
	private Connection conn;

	
	
	public DAOTablaViajes()
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


	public ArrayList<Viaje> darViajes() throws SQLException, Exception {
		ArrayList<Viaje> x = new ArrayList<Viaje>();

		String sql = "SELECT * FROM VIAJES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			x.add(new Viaje(id, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}
		return x;
	}



	public ArrayList<Viaje> buscarViajesPorId(String id) throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();

		String sql = "SELECT * FROM VIAJES WHERE ID_VIAJE ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}

		return viajes;
	}

	

	public void addViaje(Viaje viaje) throws SQLException, Exception {

		
		
		String sql = "INSERT INTO VIAJES VALUES ('";
		sql += viaje.getId() + "'" + ",'";
		sql += viaje.getPuertoOrigen() + "','";
		sql += viaje.getHoraSalida() + "',";
		sql += "TO_DATE('"+viaje.getFechaSalida()+"','YYYY-MM-DD')" +",'";
		sql += viaje.getPuertoDestino() + "','";
		sql += viaje.getHoraLlegada() + "',";
		sql += "TO_DATE('"+viaje.getFechaLLegada()+"','YYYY-MM-DD')" +",'";
		sql += viaje.getIdBuque() + "')";

		System.out.println("SQL stmt:" + sql);
		
		//TO_DATE('2012-10-09', 'YYYY-MM-DD')

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	
	
	public void updateViaje(Viaje viaje) throws SQLException, Exception {

		String sql = "UPDATE VIAJES SET ";
		sql += "ID_VIAJE='" + viaje.getId() + "',";
		sql += "PUERTO_ORIGEN='" + viaje.getPuertoOrigen() + "',";
		sql += "HORA_SALIDA='" + viaje.getHoraSalida() + "',";
		sql += "FECHA_SALIDA=" + "TO_DATE('"+viaje.getFechaSalida()+"','YYYY-MM-DD')" + ",";
		sql += "PUERTO_DESTINO='" + viaje.getPuertoDestino() + "',";
		sql += "HORA_LLEGADA='" + viaje.getHoraLlegada() + "',";
		sql += "FECHA_LLEGADA=" + "TO_DATE('"+viaje.getFechaLLegada()+"','YYYY-MM-DD')"  + ",";
		sql += "ID_BUQUE='" + viaje.getIdBuque() + "'";
		
		sql += " WHERE ID_VIAJE = " + viaje.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	
	
	public void deleteViaje(Viaje viaje) throws SQLException, Exception {

		String sql = "DELETE FROM VIAJES";
		sql += " WHERE ID_VIAJE = " + viaje.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public ArrayList<Viaje> buscarViajesQueSalen() throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();

		String sql = "SELECT * FROM VIAJES WHERE PUERTO_ORIGEN = '2'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}

		return viajes;
	}
	
	public ArrayList<Viaje> buscarViajesQueEntran() throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();

		String sql = "SELECT * FROM VIAJES WHERE PUERTO_DESTINO = '2'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}

		return viajes;
	}
	
	public ArrayList<Viaje> buscarViajesPorDestino(String id) throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();

		String sql = "SELECT * FROM Viajes WHERE PUERTO_DESTINO ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}

		return viajes;
	}

	

	public ArrayList<Viaje> buscarViajesArribosPorInfoIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		/**
		*select v.ID_VIAJE, v.PUERTO_ORIGEN, v.HORA_SALIDA, v.FECHA_SALIDA, v.PUERTO_DESTINO, v.HORA_LLEGADA,v.FECHA_LLEGADA,v.ID_BUQUE
	from BUQUES b ,VIAJES v
	where b.ID_BUQUE = v.ID_BUQUE and
	b.tipo = 'Ferri' and
	b.nombre = 'Coso'
	and v.FECHA_LLEGADA BETWEEN '08/08/08' and '10/10/10';
		*/
		double time = System.currentTimeMillis();
		double timefinal = 0;

	String sql = "SELECT  VIAJES.ID_VIAJE, VIAJES.PUERTO_ORIGEN, VIAJES.HORA_SALIDA, VIAJES.FECHA_SALIDA, VIAJES.PUERTO_DESTINO, VIAJES.HORA_LLEGADA,VIAJES.FECHA_LLEGADA,VIAJES.ID_BUQUE FROM  BUQUES  ,VIAJES   where BUQUES.ID_BUQUE = VIAJES.ID_BUQUE and VIAJES.PUERTO_DESTINO = 2 and	BUQUES.tipo = '"+tipoBuque+"' and BUQUES.nombre = '"+nombreBuque+"' and VIAJES.FECHA_LLEGADA BETWEEN '"+fechaInicial+"' and '"+ fechaFinal+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		timefinal = System.currentTimeMillis()-time;
		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}
		 
		 System.out.println("Tiempo Consulta:"+ timefinal + "Milisegundos");
		return viajes;
		
	}
	

	public ArrayList<Viaje> buscarViajesSalientesPorInfoIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		/**
		*select v.ID_VIAJE, v.PUERTO_ORIGEN, v.HORA_SALIDA, v.FECHA_SALIDA, v.PUERTO_DESTINO, v.HORA_LLEGADA,v.FECHA_LLEGADA,v.ID_BUQUE
	from BUQUES b ,VIAJES v
	where b.ID_BUQUE = v.ID_BUQUE and
	b.tipo = 'Ferri' and
	b.nombre = 'Coso'
	and v.FECHA_LLEGADA BETWEEN '08/08/08' and '10/10/10';
		*/
		
		double time = System.currentTimeMillis();
		double timefinal = 0;
	String sql = "SELECT  VIAJES.ID_VIAJE, VIAJES.PUERTO_ORIGEN, VIAJES.HORA_SALIDA, VIAJES.FECHA_SALIDA, VIAJES.PUERTO_DESTINO, VIAJES.HORA_LLEGADA,VIAJES.FECHA_LLEGADA,VIAJES.ID_BUQUE FROM  BUQUES  ,VIAJES   where BUQUES.ID_BUQUE = VIAJES.ID_BUQUE and VIAJES.PUERTO_ORIGEN = 2 and	BUQUES.tipo = '"+tipoBuque+"' and BUQUES.nombre = '"+nombreBuque+"' and VIAJES.FECHA_SALIDA BETWEEN '"+fechaInicial+"' and '"+ fechaFinal+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		  timefinal = System.currentTimeMillis()-time;
		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}
      
        System.out.println("Tiempo Consulta:"+ timefinal + "Milisegundos");
		return viajes;
	}

	public ArrayList<Viaje> buscarViajesNOSalientesPorInfoIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		/**
		*select v.ID_VIAJE, v.PUERTO_ORIGEN, v.HORA_SALIDA, v.FECHA_SALIDA, v.PUERTO_DESTINO, v.HORA_LLEGADA,v.FECHA_LLEGADA,v.ID_BUQUE
	from BUQUES b ,VIAJES v
	where b.ID_BUQUE = v.ID_BUQUE and
	b.tipo = 'Ferri' and
	b.nombre = 'Coso'
	and v.FECHA_LLEGADA BETWEEN '08/08/08' and '10/10/10';
		*/
		

	String sql = "SELECT  VIAJES.ID_VIAJE, VIAJES.PUERTO_ORIGEN, VIAJES.HORA_SALIDA, VIAJES.FECHA_SALIDA, VIAJES.PUERTO_DESTINO, VIAJES.HORA_LLEGADA,VIAJES.FECHA_LLEGADA,VIAJES.ID_BUQUE FROM  BUQUES  ,VIAJES   where BUQUES.ID_BUQUE = VIAJES.ID_BUQUE and  VIAJES.PUERTO_ORIGEN = 2 and not	BUQUES.tipo = '"+tipoBuque+"' and not BUQUES.nombre = '"+nombreBuque+"' and not VIAJES.FECHA_SALIDA BETWEEN '"+fechaInicial+"' and '"+ fechaFinal+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}

		return viajes;
	}
	public ArrayList<Viaje> buscarViajesArribosPorNOInfoIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws SQLException, Exception {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		/**
		*select v.ID_VIAJE, v.PUERTO_ORIGEN, v.HORA_SALIDA, v.FECHA_SALIDA, v.PUERTO_DESTINO, v.HORA_LLEGADA,v.FECHA_LLEGADA,v.ID_BUQUE
	from BUQUES b ,VIAJES v
	where b.ID_BUQUE = v.ID_BUQUE and
	b.tipo = 'Ferri' and
	b.nombre = 'Coso'
	and v.FECHA_LLEGADA BETWEEN '08/08/08' and '10/10/10';
		*/
		

		double time = System.currentTimeMillis();
		double timefinal = 0;
	String sql = "SELECT  VIAJES.ID_VIAJE, VIAJES.PUERTO_ORIGEN, VIAJES.HORA_SALIDA, VIAJES.FECHA_SALIDA, VIAJES.PUERTO_DESTINO, VIAJES.HORA_LLEGADA,VIAJES.FECHA_LLEGADA,VIAJES.ID_BUQUE FROM  BUQUES  ,VIAJES   where BUQUES.ID_BUQUE = VIAJES.ID_BUQUE and VIAJES.PUERTO_DESTINO = 2 and not	BUQUES.tipo = '"+tipoBuque+"' and not BUQUES.nombre = '"+nombreBuque+"' and not VIAJES.FECHA_LLEGADA BETWEEN '"+fechaInicial+"' and '"+ fechaFinal+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		timefinal = System.currentTimeMillis()-time;
		while (rs.next()) {
			String id2 = rs.getString("ID_VIAJE");
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String hs = rs.getString("HORA_SALIDA");
			Date fechaS = rs.getDate("FECHA_SALIDA");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String hll = rs.getString("HORA_LLEGADA");
			Date fechall = rs.getDate("FECHA_LLEGADA");
			String buque = rs.getString("ID_BUQUE");
			viajes.add(new Viaje(id2, puertoOrigen, hs, fechaS, puertoDestino, hll, fechall, buque));
		}
		 System.out.println("Tiempo Consulta:"+ timefinal + "Milisegundos");
		return viajes;
	}

}
