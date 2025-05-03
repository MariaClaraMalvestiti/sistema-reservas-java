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

/**
 * Clase de servicio que proporciona la lógica de negocio para gestionar reservas.
 * Actúa como intermediario entre la capa de presentación y la capa de acceso a datos.
 * Esta clase maneja la validación de datos, interacción con el usuario y operaciones CRUD
 * relacionadas con las reservas.
 */

public class ReservaService {
	private Scanner consola;
    private IReservaDAO reservaDao;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Constructor que inicializa el servicio con un objeto Scanner para la entrada de usuario
     * y crea una nueva instancia del DAO para acceder a los datos de reservas.
     * 
     * @param consola Scanner utilizado para la entrada de datos por consola
     */
    
    public ReservaService(Scanner consola) {
        this.consola = consola;
        this.reservaDao = new ReservaDAO();
    }
    
    /**
     * Muestra todas las reservas existentes en la consola.
     * 
     * @return true si existen reservas y se mostraron correctamente,
     *         false si no hay reservas disponibles.
     */
   
	public boolean mostrarReservas() {
	    List<Reserva> listadoReservas = reservaDao.listarReservas();
	    if(!listadoReservas.isEmpty()) { 
	    	listadoReservas.forEach(System.out::println);
	    	return true;
	    	}
	    System.out.println("El listado de reservas esta vacio. ");
	     return false;
	}
	
	/**
     * Busca una reserva por su ID utilizando el DAO.
     * 
     * @param reserva Objeto Reserva que contiene el ID a buscar. Si se encuentra,
     *                sus propiedades se actualizarán con los datos de la reserva encontrada.
     * @return true si la reserva fue encontrada, false en caso contrario.
     */
	
	public boolean buscarReserva(Reserva reserva) {
			return reservaDao.buscarReservaPorId(reserva);
	}
	
	/**
     * Inserta una nueva reserva en la base de datos.
     * 
     * @param reserva Objeto Reserva con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
	
	public boolean insertarReserva(Reserva reserva) {			
				return reservaDao.agregarReserva(reserva);
			}
	
	/**
     * Modifica los datos de una reserva existente.
     * 
     * @param reserva Objeto Reserva con los datos actualizados y el ID de la reserva a modificar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
			
	public boolean modificarReserva (Reserva reserva) {
				return reservaDao.actualizarReserva(reserva);
			}
	
	/**
     * Elimina una reserva de la base de datos.
     * 
     * @param reserva Objeto Reserva que contiene el ID de la reserva a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
	
	public boolean borrarReserva (Reserva reserva) {
		return reservaDao.eliminarReserva(reserva);		
	}
	
	 /**
     * Verifica si una cadena contiene solo caracteres alfabéticos.
     * 
     * @param texto Cadena a verificar.
     * @return true si la cadena no es nula, no está vacía y contiene solo letras,
     *         false en caso contrario.
     */
	
	public boolean esAlfabetico(String texto) {
		return texto != null && !texto.isEmpty() && texto.chars().allMatch(Character::isLetter);
	}
	
	/**
     * Solicita y valida el ID de una reserva a través de la consola.
     * Continúa solicitando el ID hasta que se ingrese un valor válido (entero positivo).
     * 
     * @param reserva Objeto Reserva en el que se establecerá el ID validado.
     * @return true cuando se ha completado la validación exitosamente.
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
     * Solicita y valida el nombre de la persona que realiza la reserva a través de la consola.
     * Continúa solicitando el nombre hasta que se ingrese un valor válido (solo letras).
     * 
     * @param reserva Objeto Reserva en el que se establecerá el nombre validado.
     * @return true cuando se ha completado la validación exitosamente.
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
     * Solicita y valida el apellido de la persona que realiza la reserva a través de la consola.
     * Continúa solicitando el apellido hasta que se ingrese un valor válido (solo letras).
     * 
     * @param reserva Objeto Reserva en el que se establecerá el apellido validado.
     * @return true cuando se ha completado la validación exitosamente.
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
     * Solicita y valida la fecha de la reserva a través de la consola.
     * Continúa solicitando la fecha hasta que se ingrese un valor válido en formato dd/MM/yyyy
     * y que no sea anterior a la fecha actual.
     * 
     * @param reserva Objeto Reserva en el que se establecerá la fecha validada.
     * @return true cuando se ha completado la validación exitosamente.
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
     * Solicita y valida la hora de la reserva a través de la consola.
     * Continúa solicitando la hora hasta que se ingrese un valor válido en formato HH:mm
     * y que esté dentro del horario de atención (09:00 - 21:00).
     * 
     * @param reserva Objeto Reserva en el que se establecerá la hora validada.
     * @return true cuando se ha completado la validación exitosamente.
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
