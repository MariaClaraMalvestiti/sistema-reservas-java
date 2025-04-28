package reservasDB.datos;
import reservasDB.*;

import java.util.List;

import reservasDB.dominio.Reserva;

public interface IReservaDAO {
	public List<Reserva> listarReservas();
	public boolean buscarReservaPorId(Reserva reserva);
	public boolean agregarReserva(Reserva reserva);
	public boolean actualizarReserva(Reserva reserva);
	public boolean eliminarReserva(Reserva reserva);
}
