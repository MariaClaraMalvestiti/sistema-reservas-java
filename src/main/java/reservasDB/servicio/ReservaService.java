package reservasDB.servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import reservasDB.datos.IReservaDAO;
import reservasDB.datos.ReservaDAO;
import reservasDB.dominio.Reserva;

public class ReservaService {
	private Scanner consola;
    private IReservaDAO reservaDao;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReservaService(Scanner consola) {
        this.consola = consola;
        this.reservaDao = new ReservaDAO();
    }

	public boolean mostrarReservas() {
	    List<Reserva> listadoReservas = reservaDao.listarReservas();
	    if(!listadoReservas.isEmpty()) { 
	    	listadoReservas.forEach(System.out::println);
	    	return true;
	    	}
	    System.out.println("El listado de reservas esta vacio. ");
	     return false;
	}
	
	public boolean buscarReserva(Reserva reserva) {
			return reservaDao.buscarReservaPorId(reserva);
	}
	
	public boolean insertarReserva(Reserva reserva) {			
				return reservaDao.agregarReserva(reserva);
			}
			
	public boolean modificarReserva (Reserva reserva) {
				return reservaDao.actualizarReserva(reserva);
			}
	
	public boolean borrarReserva (Reserva reserva) {
		return reservaDao.eliminarReserva(reserva);		
	}
	
	/**
     * Verifica si una cadena contiene solo caracteres alfabéticos
     */
	
	public boolean esAlfabetico(String texto) {
		return texto != null && !texto.isEmpty() && texto.chars().allMatch(Character::isLetter);
	}
	
	/**
     * Solicita y valida el ID de una reserva
     */
	
	public boolean validarId (Reserva reserva) {
		while(true) {
		try {
			System.out.print("Ingrese el ID de la reserva: ");
			int id = Integer.parseInt(consola.nextLine().trim());
			
			if(id != 0) {
				reserva.setId(id);
				break;
			}
				System.out.println("El ID no puede ser cero. Intente nuevamente. ");
			} catch (NumberFormatException e) {
				System.out.println("Error: Debe ingresar un número entero válido. ");
			}
		}
		return true;
	}
	
    /**
     * Solicita y valida el nombre de una reserva
     */
	
	public boolean validarNombreReserva (Reserva reserva) {
		while (true) {
		System.out.print("Ingrese el nombre de la reserva: ");
		String nombreReserva = consola.nextLine().trim();
			
		if (nombreReserva.isEmpty()) {
			  System.out.println("El nombre no puede estar vacio.");
			}
		
			if (esAlfabetico(nombreReserva)) {
				 reserva.setNombreReserva(nombreReserva);
				  break;
			}
			 	System.out.println("El nombre de la reserva solo puede contener letras. Intente nuevamente. ");
		}
			return true;
	}		
	/**
     * Solicita y valida el apellido de una reserva
     */
	
	public boolean validarApellidoReserva (Reserva reserva) {
		while (true) {
		System.out.print("Ingrese el apellido de la reserva: ");
		String apellidoReserva = consola.nextLine().trim();

		if (apellidoReserva.isEmpty()) {
			System.out.println("El apellido no puede estar vacio. ");
			}
		
			if (esAlfabetico(apellidoReserva)) { 
				reserva.setApellidoReserva(apellidoReserva);
				break;
			}
				System.out.println("El apellido de la reserva solo puede contener letras. Intente nuevamente.");
		}
		return true;
	}
	
	 /**
     * Solicita y valida la fecha de una reserva
     */
	
	public boolean validarFechaReserva(Reserva reserva) {
		while(true) {
		try {
			System.out.print("Ingrese la fecha de la reserva en formato (dd/MM/yyyy): ");
			String fechaStr = consola.nextLine().trim();
			
			if (fechaStr.isEmpty()) {
				System.out.println("La fecha no puede estar vacia. ");
				}
			
				LocalDate fecha = LocalDate.parse(fechaStr, DATE_FORMATTER);
			
				// Validación adicional: la fecha no puede ser anterior a hoy
				if(!fecha.isBefore(LocalDate.now())) {
					reserva.setFechaReserva(fecha);
					break;
			}
			System.out.println("La fecha no puede ser anterior a hoy. ");
		}catch (DateTimeParseException e) {
			System.out.println("Formato de fecha inválido. Utilice dd/MM/yyyy.");
			}
		}
		return true;
	}
	
	 /**
     * Solicita y valida la hora de una reserva
     */
	
	public boolean validarHoraReserva (Reserva reserva) { 
		while (true) {
		try {
			System.out.print("Ingrese la hora de la reserva en formato (HH:mm): ");
			String horaStr = consola.nextLine().trim();
			
			if(horaStr.isEmpty()) {
				System.out.println("La hora no puede estar vacia. ");
			}
			
			LocalTime hora = LocalTime.parse(horaStr); 
			
			 // Validación adicional: la hora debe estar en horario de atención
			if(!(hora.isBefore(LocalTime.of(9, 0)) || hora.isAfter(LocalTime.of(21, 0)))) {
				reserva.setHoraReserva(hora);
				break;
			}
			System.out.println("La hora debe estar entre las 08:00 y las 21:00.");
		}catch (DateTimeParseException e) {
			System.out.println("Formato de hora inválido. Utilice HH:mm. ");
			}
		}
		return true;
	}
}
