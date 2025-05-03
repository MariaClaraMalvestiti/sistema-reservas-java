package reservasDB.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import reservasDB.dominio.Reserva;
import static reservasDB.conexion.Conexion.getConexion;

/**
 * Implementación de la interfaz IReservaDAO que proporciona
 * métodos para realizar operaciones CRUD (Create, Read, Update, Delete)
 * sobre la entidad Reserva en la base de datos.
 * 
 * Esta clase gestiona la persistencia de las reservas en la base de datos,
 * incluyendo validaciones como evitar reservas duplicadas en la misma fecha y hora.
 */

public class ReservaDAO implements IReservaDAO {
		
	/**
     * Método auxiliar para cerrar recursos JDBC de manera segura.
     * Cierra en orden ResultSet, PreparedStatement y Connection.
     * 
     * @param rs ResultSet a cerrar
     * @param ps PreparedStatement a cerrar
     * @param con Connection a cerrar
     */  
	
    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
	
    /**
     * Recupera todas las reservas almacenadas en la base de datos.
     * Las reservas se devuelven ordenadas por su ID.
     * 
     * @return Lista de objetos Reserva. Si no hay reservas o ocurre un error,
     *         se devuelve una lista vacía.
     */
    
	@Override
	public List<Reserva> listarReservas() {
		List<Reserva> reservas = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
		con = getConexion();
		if (con == null) {
			  System.out.println("No se pudo establecer conexión con la base de datos.");
              return reservas;
			}
		
		String sql = "SELECT id, nombre, apellido, fecha, hora FROM reserva ORDER BY id";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
			Reserva reserva = new Reserva();
			reserva.setId(rs.getInt("id"));
			reserva.setNombreReserva(rs.getString("nombre"));
			reserva.setApellidoReserva(rs.getString("apellido"));
			reserva.setFechaReserva(rs.getDate("fecha").toLocalDate());
			reserva.setHoraReserva(rs.getTime("hora").toLocalTime());
			reservas.add(reserva);
			}
		} catch (Exception e) {
			System.out.println("Error al listar reservas. " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			 cerrarRecursos(rs, ps, con);
		}
		return reservas;
	}
	
	 /**
     * Busca una reserva por su ID y completa los datos del objeto Reserva proporcionado.
     * 
     * @param reserva Objeto Reserva con el ID a buscar. Si se encuentra, este objeto
     *                será actualizado con los datos encontrados en la base de datos.
     * @return true si la reserva fue encontrada, false en caso contrario o si ocurre un error.
     */
	
	@Override
	public boolean buscarReservaPorId(Reserva reserva) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean encontrado = false;
        
        try {
		con = getConexion();
		if (con == null) {
			 System.out.println("No se pudo establecer conexión con la base de datos.");
             return false;
		}
		
		String sql = "SELECT nombre, apellido, fecha, hora FROM reserva  WHERE id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, reserva.getId());
			rs = ps.executeQuery(); 
			
			if(rs.next()) {
				reserva.setNombreReserva(rs.getString("nombre"));
				reserva.setApellidoReserva(rs.getString("apellido"));
				reserva.setFechaReserva(rs.getDate("fecha").toLocalDate());
				reserva.setHoraReserva(rs.getTime("hora").toLocalTime());
				encontrado = true;
			}
		} catch (SQLException e) {
            System.out.println("Error al buscar reserva por ID: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }
		return encontrado;
	}
	
	 /**
     * Agrega una nueva reserva a la base de datos.
     * Valida que no exista otra reserva en la misma fecha y hora antes de insertar.
     * Si la inserción es exitosa, actualiza el ID del objeto Reserva con el generado por la base de datos.
     * 
     * @param reserva Objeto Reserva con los datos a insertar.
     * @return true si la reserva fue agregada correctamente, false si ya existe una reserva
     *         en la misma fecha y hora, o si ocurre un error durante la inserción.
     */
	
	@Override
	public boolean agregarReserva(Reserva reserva) {
		 	Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        boolean resultado = false;
	        
	        try {
	        	con = getConexion();
	        	if (con == null) {
	        		System.out.println("No se pudo establecer conexión con la base de datos.");
	                return false;
	        		}
	        	
	        	// Verificar si ya existe una reserva en la misma fecha y hora
	        	 String checkSql = "SELECT COUNT(*) FROM reserva WHERE fecha = ? AND hora = ?";
	             ps = con.prepareStatement(checkSql);
	             ps.setDate(1, java.sql.Date.valueOf(reserva.getFechaReserva()));
	             ps.setTime(2, java.sql.Time.valueOf(reserva.getHoraReserva()));
	             rs = ps.executeQuery();
	             
	             if (rs.next() && rs.getInt(1) > 0) {
	                 System.out.println("Ya existe una reserva para esa fecha y hora.");
	                 return false;
	             }
	             
	          // Cerrar recursos de la consulta anterior
	             cerrarRecursos(rs, ps, null);
	        	
	        	String insertSql = "INSERT INTO reserva (nombre, apellido, fecha, hora) VALUES (?, ?, ?, ?)";
	        	ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
	        	ps.setString(1, reserva.getNombreReserva());
	        	ps.setString(2, reserva.getApellidoReserva());
	        	ps.setDate(3, java.sql.Date.valueOf(reserva.getFechaReserva()));
	        	ps.setTime(4, java.sql.Time.valueOf(reserva.getHoraReserva()));
	        	int filasAfectadas = ps.executeUpdate();
			
			  if (filasAfectadas > 0) {
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					reserva.setId(rs.getInt(1));
					resultado = true;
				}
			}
		} catch (SQLException e) {
            System.out.println("Error al agregar reserva: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }	
	return resultado;
	}

	/**
     * Actualiza los datos de una reserva existente en la base de datos.
     * Valida que la reserva exista y que no haya otra reserva en la misma fecha y hora.
     * 
     * @param reserva Objeto Reserva con los datos actualizados. El ID debe corresponder
     *                a una reserva existente en la base de datos.
     * @return true si la reserva fue actualizada correctamente, false si no existe la reserva,
     *         ya existe otra reserva en la misma fecha y hora, o si ocurre un error durante la actualización.
     */
	
	@Override
	public boolean actualizarReserva(Reserva reserva) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean resultado = false;
	
        try {
        	con = getConexion();
        	if (con == null) {
        		 System.out.println("No se pudo establecer conexión con la base de datos.");
                 return false;
        	}
        	// Verificar si la reserva existe
        	String checkSql = "SELECT COUNT(*) FROM reserva WHERE id = ?";
            ps = con.prepareStatement(checkSql);
            ps.setInt(1, reserva.getId());
            rs = ps.executeQuery();
            
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("La reserva no existe.");
                return false;
            }

            // Cerrar recursos de la consulta anterior
            cerrarRecursos(rs, ps, null);
        	
         // Verificar si ya existe otra reserva con la misma fecha y hora (que no sea la que estamos actualizando)
            String checkDateTimeSql = "SELECT COUNT(*) FROM reserva WHERE fecha = ? AND hora = ? AND id != ?";
            ps = con.prepareStatement(checkDateTimeSql);
            ps.setDate(1, java.sql.Date.valueOf(reserva.getFechaReserva()));
            ps.setTime(2, java.sql.Time.valueOf(reserva.getHoraReserva()));
            ps.setInt(3, reserva.getId());
            rs = ps.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Ya existe otra reserva para esa fecha y hora.");
                return false;
            }
            
            // Cerrar recursos de la consulta anterior
            cerrarRecursos(rs, ps, null);
            
        	String updateSql = "UPDATE reserva SET nombre = ?, apellido = ?, fecha = ?, hora = ? WHERE id = ?";
				ps = con.prepareStatement(updateSql);
				ps.setString(1, reserva.getNombreReserva());
				ps.setString(2, reserva.getApellidoReserva());
				ps.setDate(3, java.sql.Date.valueOf(reserva.getFechaReserva()));
				ps.setTime(4, java.sql.Time.valueOf(reserva.getHoraReserva()));
				ps.setInt(5, reserva.getId());
				
				int filasActualizadas = ps.executeUpdate();
				resultado = filasActualizadas > 0;	
			}catch (SQLException e) {
	            System.out.println("Error al actualizar reserva: " + e.getMessage());
	        } finally {
	            cerrarRecursos(rs, ps, con);
	        }
		return resultado;
	}

	/**
     * Elimina una reserva de la base de datos.
     * 
     * @param reserva Objeto Reserva que contiene el ID de la reserva a eliminar.
     * @return true si la reserva fue eliminada correctamente, false si no se encontró
     *         la reserva o si ocurre un error durante la eliminación.
     */
	
	@Override
	public boolean eliminarReserva(Reserva reserva) { 
		Connection con = null;
        PreparedStatement ps = null;
        boolean resultado = false;
       
        try {
		con = getConexion();
		 if (con == null) {
             System.out.println("No se pudo establecer conexión con la base de datos.");
             return false;
		 }
		 
		String sql = "DELETE FROM reserva WHERE id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, reserva.getId());
			
			int filasEliminadas = ps.executeUpdate();
			resultado = filasEliminadas > 0;
		} catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, con);
        }
		return resultado;
	}
}

	