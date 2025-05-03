package reservasDB.conexion;
import java.sql.*;

/**
 * Clase que gestiona la conexión a la base de datos MySQL.
 * Proporciona un método estático para obtener una conexión
 * a la base de datos reservas_db.
 */

public class Conexion {
	
	  /**
     * Establece y devuelve una conexión a la base de datos MySQL.
     * 
     * @return Connection objeto de conexión a la base de datos.
     *         Retorna null si ocurre algún error durante la conexión.
     * @throws ClassNotFoundException si no se encuentra el driver de MySQL.
     * @throws SQLException si ocurre un error al conectar con la base de datos.
     */
		
	public static Connection getConexion() {
		Connection conexion = null;
		String baseDatos = "reservas_db";
		String url = "jdbc:mysql://localhost:3306/" + baseDatos;
		String usuario = "root";
		String password = "RooteR%850"; //
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, usuario, password);
			if(conexion != null) 
				System.out.println("Se conecto a la Base de Datos. ");
		}catch (ClassNotFoundException e) {
			System.out.println("No se encontró el driver de MySQL: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la Base de Datos: " + e.getMessage());
		}
		return conexion;
	}
}
