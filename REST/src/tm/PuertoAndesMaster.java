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
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import dao.DAOTablaAreasAlmacenamiento;
import dao.DAOTablaBodegas;
import dao.DAOTablaBuques;
import dao.DAOTablaCargas;
import dao.DAOTablaCobertizos;
import dao.DAOTablaExportadores;
import dao.DAOTablaFacturas;
import dao.DAOTablaImportadores;
import dao.DAOTablaMovimientos;
import dao.DAOTablaMovimientosArea;
import dao.DAOTablaPatios;
import dao.DAOTablaPuertos;
import dao.DAOTablaSilos;
import dao.DAOTablaViajes;
import vos.AreaAlmacenamiento;
import vos.Bodega;
import vos.Buque;
import vos.Carga;
import vos.Cobertizo;
import vos.Exportador;
import vos.Factura;
import vos.Importador;
import vos.ListaAreaAlmacenamiento;
import vos.ListaBodegas;
import vos.ListaBuques;
import vos.ListaCarga;
import vos.ListaCobertizos;
import vos.ListaExportadores;
import vos.ListaFacturas;
import vos.ListaImportador;
import vos.ListaMovimientos;
import vos.ListaMovimientosArea;
import vos.ListaPatios;
import vos.ListaPuertos;
import vos.ListaSilos;
import vos.ListaViajes;
import vos.MovimientoAreaAlmacenamiento;
import vos.MovimientoCarga;
import vos.Patio;
import vos.Puerto;
import vos.Silo;
import vos.Viaje;


public class PuertoAndesMaster {



	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	
	private  String connectionDataPath;

	private String user;

	
	private String password;

	private String url;

	
	private String driver;
	
	
	
	private Connection conn;


	public PuertoAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		
		}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	public void descargarBuque (Carga carga) throws Exception
	{

			try
			{
			
			String elegido ="";
			String idBuque = carga.getIdBuque();
			Buque actual = buscarBuquePorId(idBuque).getBuques().get(0);
			
			String tipo = carga.getTipo();	
			int volumen = Integer.parseInt(carga.getVolumenCarga());
			ArrayList bodegas= new ArrayList<>();
			ArrayList silos= new ArrayList<>();
			ArrayList cobertizos= new ArrayList<>();
			ArrayList patios= new ArrayList<>();
			
			bodegas = darBodegasDisponibles();
			silos =darSilosDisponibles();
			cobertizos = darCobertizosDisponibles();
			patios= darPatiosDisponibles();
			
			for ( int i = 0 ; i< cobertizos.size() && elegido.equals("");i++)
			{
				Cobertizo cobe = (Cobertizo) cobertizos.get(i);
				if (cobe.getTipoCarga().equalsIgnoreCase(tipo))
				{
					elegido= cobe.getId()+"&COBERTIZO";
					
				}
			}
       			for ( int i = 0 ; i< patios.size() && elegido.equals("");i++)
			{
				Patio patio = (Patio) patios.get(i);
				if (patio.getTipoCarga().equalsIgnoreCase(tipo))
				{
					elegido= patio.getId()+"&PATIO";
					
				}
			}
			
			for ( int i = 0 ; i< bodegas.size() && elegido.equals("");i++)
			{
				Bodega bodega = (Bodega) bodegas.get(i);
				int x = Integer.parseInt(bodega.getAreaCuartoBodega());
				int alto = Integer.parseInt(bodega.getAlto());
				if (x*alto>=volumen)
				{
					elegido= bodega.getId()+"&BODEGA";
					
				}
			}
			
			
			for ( int i = 0 ; i< silos.size() && elegido.equals("");i++)
			{
				Silo silo = (Silo) silos.get(i);
				int capacidadSilo = Integer.parseInt(silo.getCapacidad());
				
				if (capacidadSilo>=volumen)
				{
					elegido= silo.getId()+"&SILO";
					
				}
			}
			
			
			String[] area = elegido.split("&");
			
			if (area[1].equals("COBERTIZO"))
			{
				String id = area[0];
				Cobertizo cobertizodefinitivo =(Cobertizo) buscarCobertizoPorId(id).getCobertizos().get(0);
				cobertizodefinitivo.setESTADO("OCUPADO");
				updateCobertizo(cobertizodefinitivo);
				actual.setEstado("DESCARGANDO");
				updateBuque(actual);
				carga.setAlma(cobertizodefinitivo.getId());
				System.out.println(carga.getAlma());
				entregarCarga(carga);				
				actual.setEstado("DISPONIBLE");
				updateBuque(actual);
				java.util.Date  fecha = new Date();
				java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                MovimientoCarga mov  = new MovimientoCarga("id", carga.getId(), "Buque:" + idBuque, "COBERTIZO:"+cobertizodefinitivo.getId(), fechaSQL );
               addMovimiento(mov);
               MovimientoAreaAlmacenamiento almac = new MovimientoAreaAlmacenamiento(cobertizodefinitivo.getId(), "Cobertizo", carga.getId() , "Entra", fechaSQL);
               addMovimientoArea(almac);
                
			}
			if (area[1].equals("PATIO"))
			{
				String id = area[0];
				Patio patiodefinitivo =(Patio) buscarPatioPorId(id).getPatios().get(0);
				patiodefinitivo.setESTADO("OCUPADO");
				updatePatio(patiodefinitivo);
				actual.setEstado("DESCARGANDO");
				updateBuque(actual);
				carga.setAlma(patiodefinitivo.getId());
				System.out.println(carga.getAlma());
				entregarCarga(carga);				
				actual.setEstado("DISPONIBLE");
				updateBuque(actual);
				java.util.Date  fecha = new Date();
				java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                MovimientoCarga mov  = new MovimientoCarga("id", carga.getId(), "Buque:" + idBuque, "PATIO: "+patiodefinitivo.getId(), fechaSQL );
                addMovimiento(mov);
                MovimientoAreaAlmacenamiento almac = new MovimientoAreaAlmacenamiento(patiodefinitivo.getId(), "Patio", carga.getId() , "Entra", fechaSQL);
                addMovimientoArea(almac);
			}
			if (area[1].equals("BODEGA"))
			{
				String id = area[0];
				Bodega bodegadefinitiva =(Bodega) buscarBodegaPorId(id).getBodegas().get(0);
				bodegadefinitiva.setEstado("OCUPADO");
				updateBodega(bodegadefinitiva);
				actual.setEstado("DESCARGANDO");
				updateBuque(actual);
				carga.setAlma(bodegadefinitiva.getId());
				System.out.println(carga.getAlma());
				entregarCarga(carga);				
				actual.setEstado("DISPONIBLE");
				updateBuque(actual);
				java.util.Date  fecha = new Date();
				java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                MovimientoCarga mov  = new MovimientoCarga("id", carga.getId(), "Buque:" + idBuque, "BODEGA:" + bodegadefinitiva.getId(), fechaSQL );
                addMovimiento(mov);
                MovimientoAreaAlmacenamiento almac = new MovimientoAreaAlmacenamiento(bodegadefinitiva.getId(), "Bodega", carga.getId() , "Entra", fechaSQL);
                addMovimientoArea(almac);
			}
			if (area[1].equals("SILO"))
			{
				String id = area[0];
				Silo silodefinitiva =(Silo) buscarSiloPorId(id).getSilos().get(0);
				silodefinitiva.setESTADO("OCUPADO");
				updateSilo(silodefinitiva);
				actual.setEstado("DESCARGANDO");
				updateBuque(actual);
				carga.setAlma(silodefinitiva.getId());
				System.out.println(carga.getAlma());
				entregarCarga(carga);				
				actual.setEstado("DISPONIBLE");
				updateBuque(actual);
				java.util.Date  fecha = new Date();
				java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
                MovimientoCarga mov  = new MovimientoCarga("id", carga.getId(), "Buque:" + idBuque, "SILO:" + silodefinitiva.getId(), fechaSQL );
                addMovimiento(mov);
                MovimientoAreaAlmacenamiento almac = new MovimientoAreaAlmacenamiento(silodefinitiva.getId(), "Silo", carga.getId() , "Entra", fechaSQL);
                addMovimientoArea(almac);
			}
		
			} catch (Exception e)
			{
				this.conn.rollback();
				e.printStackTrace();
			}

			
			
				
				
			
			
			
			

		
		
		
	}
	public ListaPuertos darPuertos() throws Exception {
		ArrayList<Puerto> puertos;
		DAOTablaPuertos daoPuertos = new DAOTablaPuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			puertos = daoPuertos.darPuertos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPuertos(puertos);
	}

	
	public ListaPuertos buscarPuertoPorId(String id) throws Exception {
		ArrayList<Puerto> puertos;
		DAOTablaPuertos daoPuertos = new DAOTablaPuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			puertos = daoPuertos.buscarPuertosPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPuertos(puertos);
	}

	
	public void addPuerto(Puerto puerto) throws Exception {
		DAOTablaPuertos daoPuertos = new DAOTablaPuertos();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			daoPuertos.addPuerto(puerto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addPuertos(ListaPuertos puertos) throws Exception {
		DAOTablaPuertos daoPuertos = new DAOTablaPuertos();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPuertos.setConn(conn);
			for(Puerto puerto : puertos.getPuertos())
				daoPuertos.addPuerto(puerto);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updatePuerto(Puerto puerto) throws Exception {
		DAOTablaPuertos daoPuertos = new DAOTablaPuertos();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			daoPuertos.updatePuerto(puerto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deletePuerto(Puerto puerto) throws Exception {
		DAOTablaPuertos daoPuertos = new DAOTablaPuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			daoPuertos.deletePuerto(puerto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public ListaBuques darBuques() throws Exception {
		ArrayList<Buque> buques;
		DAOTablaBuques daobuques = new DAOTablaBuques();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobuques.setConn(conn);
			buques = daobuques.darBuques();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBuques(buques);
	}

	
	public ListaBuques buscarBuquePorId(String id) throws Exception {
		ArrayList<Buque> buques;
		DAOTablaBuques daobuques = new DAOTablaBuques();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobuques.setConn(conn);
			buques = daobuques.buscarBuquesPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBuques(buques);
	}

	
	public void addBuque(Buque buque) throws Exception {
		DAOTablaBuques daobuques = new DAOTablaBuques();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobuques.setConn(conn);
			daobuques.addBuque(buque);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addBuques(ListaBuques buques) throws Exception {
		DAOTablaBuques daobuques = new DAOTablaBuques();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daobuques.setConn(conn);
			for(Buque buque : buques.getBuques())
				daobuques.addBuque(buque);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daobuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updateBuque(Buque buque) throws Exception {
		DAOTablaBuques daobuques = new DAOTablaBuques();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobuques.setConn(conn);
			daobuques.updateBuque(buque);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deleteBuque(Buque buque) throws Exception {
		DAOTablaBuques daobuques = new DAOTablaBuques();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobuques.setConn(conn);
			daobuques.deleteBuque(buque);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

}

	
	
	
	
	
	
	
	
	
	
	
	public ListaImportador darImportadores() throws Exception {
		ArrayList<Importador> importadores;
		DAOTablaImportadores daoImportadores = new DAOTablaImportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoImportadores.setConn(conn);
			importadores = daoImportadores.darImportadores();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoImportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaImportador(importadores);
	}

	public ListaImportador buscarImportadorPorId(String id) throws Exception {
		ArrayList<Importador> importadores;
		DAOTablaImportadores daoImportadores = new DAOTablaImportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoImportadores.setConn(conn);
			importadores = daoImportadores.buscarImportadoresPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoImportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaImportador(importadores);
	}

	public void addImportador(Importador importador) throws Exception {
		DAOTablaImportadores daoImportadores = new DAOTablaImportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoImportadores.setConn(conn);
			daoImportadores.addImportador(importador);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoImportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void addImportadores(ListaImportador importadores) throws Exception {
		DAOTablaImportadores daoImportadores = new DAOTablaImportadores();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoImportadores.setConn(conn);
			for(Importador importador : importadores.getimportadores())
				daoImportadores.addImportador(importador);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoImportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void updateImportador(Importador importador) throws Exception {
		DAOTablaImportadores daoImportadores = new DAOTablaImportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoImportadores.setConn(conn);
			daoImportadores.updateImportador(importador);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoImportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteImportador(Importador importador) throws Exception {
		DAOTablaImportadores daoImportadores = new DAOTablaImportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoImportadores.setConn(conn);
			daoImportadores.deleteImportador(importador);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoImportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	public ListaExportadores darExportadores() throws Exception {
		ArrayList<Exportador> exportadores;
		DAOTablaExportadores daoexportadores = new DAOTablaExportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoexportadores.setConn(conn);
			exportadores = daoexportadores.darExportadores();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoexportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaExportadores(exportadores);
	}

	
	public ListaExportadores buscarExportadorPorId(String id) throws Exception {
		ArrayList<Exportador> exportadores;
		DAOTablaExportadores daoexportadores = new DAOTablaExportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoexportadores.setConn(conn);
			exportadores = daoexportadores.buscarExportadoresPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoexportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaExportadores(exportadores);
	}

	
	public void addExportador(Exportador exportador) throws Exception {
		DAOTablaExportadores daoexportadores = new DAOTablaExportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoexportadores.setConn(conn);
			daoexportadores.addExportador(exportador);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoexportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addExportadores(ListaExportadores exportadores) throws Exception {
		DAOTablaExportadores daoexportadores = new DAOTablaExportadores();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoexportadores.setConn(conn);
			for(Exportador exportador : exportadores.getExportadores())
				daoexportadores.addExportador(exportador);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoexportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updateExportador(Exportador exportador) throws Exception {
		DAOTablaExportadores daoexportadores = new DAOTablaExportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoexportadores.setConn(conn);
			daoexportadores.updateExportador(exportador);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoexportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deleteExportador(Exportador exportador) throws Exception {		
		DAOTablaExportadores daoexportadores = new DAOTablaExportadores();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoexportadores.setConn(conn);
			daoexportadores.deleteExportador(exportador);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoexportadores.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	
	
	
	public ListaBodegas darBodegas() throws Exception {
		ArrayList<Bodega> bodegas;
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobodegas.setConn(conn);
			bodegas = daobodegas.darBodegas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBodegas(bodegas);
	}
	
	public ArrayList darBodegasDisponibles() throws Exception {
		ArrayList bodegas;
		ArrayList crea = new ArrayList();
		ListaBodegas resp = new ListaBodegas(crea);
		ArrayList aux = new ArrayList();
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobodegas.setConn(conn);
			bodegas = daobodegas.darBodegas();
			for ( int i = 0 ; i<=bodegas.size()-1; i++)
			{
				Bodega b = (Bodega) bodegas.get(i);
				if ( b.getEstado().equalsIgnoreCase("DISPONIBLE"))
				{
					aux.add(b);
					
				}
			}
			resp.setBodega(aux);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return (ArrayList) resp.getBodegas();
	}

	

	public ArrayList darPatiosDisponibles() throws Exception {
			ArrayList Patios;
			ArrayList crea = new ArrayList();
			ListaPatios resp = new ListaPatios(crea);
			ArrayList aux = new ArrayList();
			DAOTablaPatios daoPatios = new DAOTablaPatios();
			try 
			{
				//////Transacción
				this.conn = darConexion();
				daoPatios.setConn(conn);
				Patios = daoPatios.darPatios();
				for ( int i = 0 ; i<=Patios.size()-1; i++)
				{
					Patio b = (Patio) Patios.get(i);
					if ( b.getESTADO().equalsIgnoreCase("DISPONIBLE"))
					{
						aux.add(b);
						
					}
				}
				resp.setPatio(aux);
				

			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoPatios.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return (ArrayList) resp.getPatios();
		}
	
	
	
	
	public ArrayList darCobertizosDisponibles() throws Exception {
		ArrayList Cobertizos;
		ArrayList crea = new ArrayList();
		ListaCobertizos resp = new ListaCobertizos(crea);
		ArrayList aux = new ArrayList();
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCobertizos.setConn(conn);
			Cobertizos = daoCobertizos.darCobertizos();
			for ( int i = 0 ; i<=Cobertizos.size()-1; i++)
			{
				Cobertizo b = (Cobertizo) Cobertizos.get(i);
				if ( b.getESTADO().equalsIgnoreCase("DISPONIBLE"))
				{
					aux.add(b);
					
				}
			}
			resp.setCobertizo(aux);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return (ArrayList) resp.getCobertizos();
	}
	
	
	
	
	
	public ArrayList darSilosDisponibles() throws Exception {
		ArrayList Silos;
		ArrayList crea = new ArrayList();
		ListaSilos resp = new ListaSilos(crea);
		ArrayList aux = new ArrayList();
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSilos.setConn(conn);
			Silos = daoSilos.darSilos();
			for ( int i = 0 ; i<=Silos.size()-1; i++)
			{
				Silo b = (Silo) Silos.get(i);
				if ( b.getESTADO().equalsIgnoreCase("DISPONIBLE"))
				{
					aux.add(b);
					
				}
			}
			resp.setSilo(aux);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return (ArrayList) resp.getSilos();
	}
	
	
	public ListaBodegas buscarBodegaPorId(String id) throws Exception {
		ArrayList<Bodega> bodegas;
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobodegas.setConn(conn);
			bodegas = daobodegas.buscarBodegasPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBodegas(bodegas);
	}

	
	public void addBodega(Bodega bodega) throws Exception {
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobodegas.setConn(conn);
			daobodegas.addBodega(bodega);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addBodegas(ListaBodegas bodegas) throws Exception {
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daobodegas.setConn(conn);
			for(Bodega bodega : bodegas.getBodegas())
				daobodegas.addBodega(bodega);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updateBodega(Bodega bodega) throws Exception {
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobodegas.setConn(conn);
			daobodegas.updateBodega(bodega);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deleteBodega(Bodega bodega) throws Exception {
		DAOTablaBodegas daobodegas = new DAOTablaBodegas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daobodegas.setConn(conn);
			daobodegas.deleteBodega(bodega);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daobodegas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	public ListaAreaAlmacenamiento darAreasAlmacenamiento() throws Exception {
		ArrayList<AreaAlmacenamiento> viajes;
		DAOTablaAreasAlmacenamiento daoViajes = new DAOTablaAreasAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.darAreasAlmacenamiento();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAreaAlmacenamiento(viajes);
	}
	
	
	

	
	public ListaAreaAlmacenamiento buscarAreaAlmacenamientoPorId(String id) throws Exception {
		ArrayList<AreaAlmacenamiento> viajes;
		DAOTablaAreasAlmacenamiento daoViajes = new DAOTablaAreasAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarAreasAlmacenamientoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAreaAlmacenamiento(viajes);
	}
	
	

	
	public void addAreaAlmacenamiento(String id, String tipo) throws Exception {
		DAOTablaAreasAlmacenamiento daoViajes = new DAOTablaAreasAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			daoViajes.addAreaAlmacenamiento(id, tipo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	public ListaViajes darViajes() throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.darViajes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}
	
	
	public ListaViajes darViajesQueSalen() throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarViajesQueSalen();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}

	
	public ListaViajes darViajesQueEntranConInfroIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarViajesArribosPorInfoIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}
	public ListaViajes darViajesQueEntranConNoInfroIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarViajesArribosPorNOInfoIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}

	public ListaMovimientosArea darMovimientosAreasPorAreas(String idArea1, String idArea2) throws Exception {
		ArrayList<MovimientoAreaAlmacenamiento> mov;
		DAOTablaMovimientosArea daoMov = new DAOTablaMovimientosArea();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMov.setConn(conn);
			mov = daoMov.buscarMovimientoPorAreas(idArea1, idArea2);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMov.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaMovimientosArea(mov);
	}
	
	
	public ListaViajes darViajesQueSalenConInfroIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarViajesSalientesPorInfoIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}
	
	public ListaViajes darViajesQueSalenConNOInfroIT4(String nombreBuque, String tipoBuque,String fechaInicial, String fechaFinal) throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarViajesNOSalientesPorInfoIT4(nombreBuque, tipoBuque, fechaInicial, fechaFinal);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}
	
	public ListaMovimientos darMovimientosEspecificos(String valor, String tipoCarga) throws Exception {
		ArrayList<MovimientoCarga> mov;
		DAOTablaMovimientos daoMov = new DAOTablaMovimientos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMov.setConn(conn);
			mov = daoMov.buscarMovimientoEspecifico(valor, tipoCarga);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMov.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaMovimientos(mov);
	}
	public ListaViajes buscarViajePorId(String id) throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			viajes = daoViajes.buscarViajesPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajes(viajes);
	}
	
	

	
	public void addViaje(Viaje viaje) throws Exception {
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			daoViajes.addViaje(viaje);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addViajes(ListaViajes viajes) throws Exception 
	{
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoViajes.setConn(conn);
			for(Viaje viaje : viajes.getViajes())
				daoViajes.addViaje(viaje);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updateViaje(Viaje viaje) throws Exception {
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			daoViajes.updateViaje(viaje);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deleteViaje(Viaje viaje) throws Exception {
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			daoViajes.deleteViaje(viaje);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public void addCobertizo(Cobertizo Cobertizo) throws Exception {
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCobertizos.setConn(conn);
			
		
			daoCobertizos.addCobertizo(Cobertizo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addCobertizos(ListaCobertizos Cobertizos) throws Exception {
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoCobertizos.setConn(conn);
			for(Cobertizo Cobertizo : Cobertizos.getCobertizos())
				daoCobertizos.addCobertizo(Cobertizo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updateCobertizo(Cobertizo Cobertizo) throws Exception {
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCobertizos.setConn(conn);
			daoCobertizos.updateCobertizo(Cobertizo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deleteCobertizo(Cobertizo Cobertizo) throws Exception {
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCobertizos.setConn(conn);
			daoCobertizos.deleteCobertizo(Cobertizo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

}
	
	public ListaCobertizos darCobertizos() throws Exception {
		ArrayList<Cobertizo> Cobertizos;
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCobertizos.setConn(conn);
			Cobertizos = daoCobertizos.darCobertizos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCobertizos(Cobertizos);
	}
	
	public ListaCobertizos buscarCobertizoPorId(String id) throws Exception {
		ArrayList<Cobertizo> Cobertizos;
		DAOTablaCobertizos daoCobertizos = new DAOTablaCobertizos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCobertizos.setConn(conn);
			Cobertizos = daoCobertizos.buscarCobertizoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCobertizos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCobertizos(Cobertizos);
	}
	
	
	
	
	

	public ListaPatios darPatios() throws Exception {
		ArrayList<Patio> Patios;
		DAOTablaPatios daoPatios = new DAOTablaPatios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPatios.setConn(conn);
			Patios = daoPatios.darPatios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPatios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPatios(Patios);
	}

	
	public ListaPatios buscarPatioPorId(String id) throws Exception {
		ArrayList<Patio> Patios;
		DAOTablaPatios daoPatios = new DAOTablaPatios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPatios.setConn(conn);
			Patios = daoPatios.buscarPatioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPatios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPatios(Patios);
	}

	
	public void addPatio(Patio Patio) throws Exception {
		DAOTablaPatios daoPatios = new DAOTablaPatios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPatios.setConn(conn);
			daoPatios.addPatio(Patio);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPatios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addPatios(ListaPatios Patios) throws Exception {
		DAOTablaPatios daoPatios = new DAOTablaPatios();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPatios.setConn(conn);
			for(Patio Patio : Patios.getPatios())
				daoPatios.addPatio(Patio);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoPatios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updatePatio(Patio Patio) throws Exception {
		DAOTablaPatios daoPatios = new DAOTablaPatios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPatios.setConn(conn);
			daoPatios.updatePatio(Patio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPatios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deletePatio(Patio Patio) throws Exception {
		DAOTablaPatios daoPatios = new DAOTablaPatios();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPatios.setConn(conn);
			daoPatios.deletePatio(Patio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPatios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}

	public ListaSilos darSilos() throws Exception {
		ArrayList<Silo> Silos;
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSilos.setConn(conn);
			Silos = daoSilos.darSilos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSilos(Silos);
	}

	
	public ListaSilos buscarSiloPorId(String id) throws Exception {
		ArrayList<Silo> Silos;
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSilos.setConn(conn);
			Silos = daoSilos.buscarSiloPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSilos(Silos);
	}

	
	public void addSilo(Silo Silo) throws Exception {
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSilos.setConn(conn);
			daoSilos.addSilo(Silo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addSilos(ListaSilos Silos) throws Exception {
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoSilos.setConn(conn);
			for(Silo Silo : Silos.getSilos())
				daoSilos.addSilo(Silo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void updateSilo(Silo Silo) throws Exception {
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSilos.setConn(conn);
			daoSilos.updateSilo(Silo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public void deleteSilo(Silo Silo) throws Exception {
		DAOTablaSilos daoSilos = new DAOTablaSilos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSilos.setConn(conn);
			daoSilos.deleteSilo(Silo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSilos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	public ListaCarga darCargas() throws Exception {
		ArrayList<Carga> cargas;
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			cargas = daoCargas.darCargas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCarga(cargas);
	}

	
	public ListaCarga buscarCargaPorIdBuque(String id) throws Exception {
		ArrayList<Carga> cargas;
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			cargas = daoCargas.buscarCargasPorIdBuque(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCarga(cargas);
	}
	
	public ListaCarga buscarCargaPorAreaAlamacenamiento(String id) throws Exception {
		ArrayList<Carga> cargas;
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			cargas = daoCargas.buscarCargasPorIdAlmacenamiento(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCarga(cargas);
	}
	
	
	public ListaCarga buscarCargaPorId(String id) throws Exception {
		ArrayList<Carga> cargas;
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			cargas = daoCargas.buscarCargasPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCarga(cargas);
	}
	
	public ListaCarga buscarTODO(String id) throws Exception {
		ArrayList<Carga> cargas;
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			cargas = daoCargas.buscarCargasPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCarga(cargas);
	}


	
	public void addCarga(Carga carga) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			daoCargas.addCarga(carga);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	public void addCargas(ListaCarga cargas) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoCargas.setConn(conn);
			for(Carga carga : cargas.getcargas())
				daoCargas.addCarga(carga);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	public void entregarCarga(Carga carga) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			daoCargas.insertCarga(carga);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	
	public void updateCarga(Carga carga) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCargas.setConn(conn);
			daoCargas.updateCarga(carga);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCargas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void deleteCarga(Carga carga) throws Exception {
		DAOTablaCargas daoCarga = new DAOTablaCargas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCarga.setConn(conn);
			daoCarga.deleteCarga(carga);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	public ListaFacturas darFacturas() throws Exception {
		ArrayList<Factura> facturas;
		DAOTablaFacturas daoFacturas = new DAOTablaFacturas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFacturas.setConn(conn);
			facturas = daoFacturas.darFacturas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFacturas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFacturas(facturas);
	}

	
	public ListaFacturas buscarFacturaPorIdCarga(String id) throws Exception {
		ArrayList<Factura> facturas;
		DAOTablaFacturas daoFacturas = new DAOTablaFacturas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFacturas.setConn(conn);
			facturas = daoFacturas.buscarFacturaPorCarga(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFacturas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFacturas(facturas);
	}

	
	public void addFactura(Factura factura) throws Exception {
		DAOTablaFacturas daoFacturas = new DAOTablaFacturas();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFacturas.setConn(conn);
			daoFacturas.addFactura(factura);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFacturas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	

	

	
	public void deleteFactura(Factura factura) throws Exception {
		DAOTablaFacturas daoFacturas = new DAOTablaFacturas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFacturas.setConn(conn);
			daoFacturas.deleteFactura(factura);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFacturas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
		
	}
	
	public void cargarBuque (String id, Carga carga) throws Exception {
		ArrayList<Viaje> viajes;
		DAOTablaViajes daoViajes = new DAOTablaViajes();
		DAOTablaCargas daoCarga = new DAOTablaCargas(); 
		DAOTablaBuques daoBuque = new DAOTablaBuques();
		DAOTablaMovimientos daoMov = new DAOTablaMovimientos(); 
		DAOTablaMovimientosArea daoAream = new DAOTablaMovimientosArea();
	
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajes.setConn(conn);
			daoCarga.setConn(conn);
			daoBuque.setConn(conn);
			daoMov.setConn(conn);
			daoAream.setConn(conn);
		
			viajes = daoViajes.buscarViajesPorDestino(id);
			Carga c = daoCarga.buscarCargasPorId(carga.getId()).get(0);
			boolean termino = false;
			for (int i = 0; i < viajes.size() && termino==false; i++) 
			{
				
				Viaje temp = viajes.get(i);
				ArrayList<Buque> y = daoBuque.buscarBuquesPorId(temp.getIdBuque());
				Buque x = y.get(0);
				int cap = Integer.parseInt(x.getCapacidad());
				int tamCarga = Integer.parseInt(c.getVolumenCarga());
				System.out.println("paso carga");
				if (cap == 0)
				{
					x.setEstado("LLeno");
					daoBuque.updateBuque(x);
			
					
					
				}
				if(cap>=tamCarga)
				{
					
					java.util.Date  fecha = new Date();
					java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
					MovimientoCarga movi = new MovimientoCarga("id",c.getId(),"Area:"+c.getAlma(), "Buque: "+x.getId(), fechaSQL);
					daoMov.addMovimiento(movi);
					MovimientoAreaAlmacenamiento aream = new MovimientoAreaAlmacenamiento(c.getId(), "Desconocida ", c.getId(), "Sale", fechaSQL);
					daoAream.addMovimiento(aream);
					
					c.setAlma("");
					c.setIdBuque(x.getId());
					x.setCapacidad((""+(cap-tamCarga)+""));
					x.setEstado("En Carga");
					daoBuque.updateBuque(x);
					daoCarga.updateCarga(c);
					System.out.println("id"+c.getId()+c.getAlma()+ x.getId()+ fechaSQL);
					System.out.println(c.getAlma() + "ohhhhhhhhhhhhhh");
					
					


				}
				if(cap<=tamCarga)
				{
					throw new Exception("La carga no cabe en el barco"); 
				}
				x.setEstado("Carga Finalizada");
				daoBuque.updateBuque(x);
			}

			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();conn.rollback();
			throw e;

		} finally {
			try {
				daoViajes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	
	public void addMovimiento(MovimientoCarga mov) throws Exception {
		DAOTablaMovimientos daoMov = new DAOTablaMovimientos();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMov.setConn(conn);
			daoMov.addMovimiento(mov);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMov.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public ListaMovimientos darMovimientos() throws Exception {
		ArrayList<MovimientoCarga> mov;
		DAOTablaMovimientos daoMov = new DAOTablaMovimientos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMov.setConn(conn);
			mov = daoMov.darMovimientos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMov.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaMovimientos(mov);
	}


	public void addMovimientoArea(MovimientoAreaAlmacenamiento mov) throws Exception {
		DAOTablaMovimientosArea	daoMov = new DAOTablaMovimientosArea();
		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMov.setConn(conn);
			daoMov.addMovimiento(mov);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMov.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	
	public ListaMovimientosArea darMovimientosAreas() throws Exception {
		ArrayList<MovimientoAreaAlmacenamiento> mov;
		DAOTablaMovimientosArea daoMov = new DAOTablaMovimientosArea();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMov.setConn(conn);
			mov = daoMov.darMovimientos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMov.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaMovimientosArea(mov);
	}

	
	
	public void deshabilitarBuque(Buque malo) throws SQLException, Exception
	{
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		this.conn = darConexion();
		daoCargas.setConn(conn);
		daoBuques.setConn(conn);
		this.conn.setAutoCommit(false);
//		Savepoint erro = this.conn.setSavepoint("Error");
		
		
		ArrayList cargas = daoCargas.buscarCargasPorIdBuque(malo.getId());
		
		for (int i = 0; i < cargas.size(); i++) 
		{
			

			try
			{
			descargarBuque((Carga) cargas.get(i));
//			erro = this.conn.setSavepoint("Error");
			malo.setEstado("DESHABILITADO");
			daoBuques.updateBuque(malo);
			
			}
			catch (Exception x )
			{
				x.printStackTrace();
				this.conn.rollback();
			}
		}
		
		
		
	}
	public void deshabilitarArea(AreaAlmacenamiento malo) throws SQLException, Exception
	{
		
		String tipo = malo.getTipo();
		
	
				
		ArrayList cargas = (ArrayList) buscarCargaPorAreaAlamacenamiento(malo.getId()).getcargas();	
		ArrayList bode =darBodegasDisponibles();
		ArrayList pai = darPatiosDisponibles();
		ArrayList cob = darCobertizosDisponibles();
		ArrayList sil = darSilosDisponibles();
		
		
		if (tipo.equals("COBERTIZO"))
		{

			Cobertizo w  = buscarCobertizoPorId(malo.getId()).getCobertizos().get(0);
			if (w != null)
			{
				w.setESTADO("DESHABILITADO");
				updateCobertizo(w);
				for (int i = 0; i < cargas.size(); i++) 
				{
					Carga car = (Carga) cargas.get(i);
					Cobertizo u = (Cobertizo) cob.get(0);
					if ( u!= null)
					{
						car.setAlma(u.getId());
						u.setESTADO("OCUPADO");
						updateCobertizo(u);
						updateCarga(car);
						
					}
					else 
					{
						throw new Exception("Llorelo no hay mas cobertizos disponibles :C");
					}
				
					
				}
			}
		}
		if (tipo.equals("PATIO"))
		{

			Patio z  = buscarPatioPorId(malo.getId()).getPatios().get(0);
			if (z != null)
			{
				z.setESTADO("DESHABILITADO");
				updatePatio(z);
				for (int i = 0; i < cargas.size(); i++) 
				{
					Carga car = (Carga) cargas.get(i);
					Patio u = (Patio) pai.get(0);
					if ( u!= null)
					{
						car.setAlma(u.getId());
						u.setESTADO("OCUPADO");
						updatePatio(u);
						updateCarga(car);
						
					}
				
					
				}
			}
		}
		if (tipo.equals("SILO"))
		{

			Silo y  = buscarSiloPorId(malo.getId()).getSilos().get(0);
			if (y != null)
			{
				y.setESTADO("DESHABILITADO");
				updateSilo(y);
				for (int i = 0; i < cargas.size(); i++) 
				{
					Carga car = (Carga) cargas.get(i);
					Silo u = (Silo) sil.get(0);
					if ( u!= null)
					{
						car.setAlma(u.getId());
						u.setESTADO("OCUPADO");
						updateSilo(u);
						updateCarga(car);
						
					}
				
					
				}
			}
		}
		if (tipo.equals("Bodega"))
		{

			Bodega x  = buscarBodegaPorId(malo.getId()).getBodegas().get(0);
			if (x != null)
			{
				x.setEstado("DESHABILITADO");
				updateBodega(x);
				for (int i = 0; i < cargas.size(); i++) 
				{
					Carga car = (Carga) cargas.get(i);
					Bodega u = (Bodega) bode.get(0);
					if ( u!= null)
					{
						car.setAlma(u.getId());
						u.setEstado("OCUPADO");
						updateBodega(u);
						updateCarga(car);
						
					}
				
					
				}
			}
		}
		
		
		
		
	
	
	
		
		
	}
	
	
	
	
	



}
