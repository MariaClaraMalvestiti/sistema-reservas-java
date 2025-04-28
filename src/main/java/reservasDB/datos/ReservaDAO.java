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

public class ReservaDAO implements IReservaDAO {
	
	   /**
     * Método auxiliar para cerrar recursos JDBC de manera segura
     */
	
    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
	
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

	