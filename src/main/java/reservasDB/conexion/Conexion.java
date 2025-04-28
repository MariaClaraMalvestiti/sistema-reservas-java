package reservasDB.conexion;
import java.sql.*;


public class Conexion {
	public static Connection getConexion() {
		Connection conexion = null;
		String baseDatos = "reservas_db";
		String url = "jdbc:mysql://localhost:3306/" + baseDatos;
		String usuario = "root";
		String password = "RooteR%850";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, usuario, password);
			if(conexion != null) 
				System.out.println("Se conecto a la Base de Datos. ");
		}catch (Exception e) {
			System.out.println("Error al conectar la Base de Datos." + e.getMessage());
		}
		return conexion;
	}
}
