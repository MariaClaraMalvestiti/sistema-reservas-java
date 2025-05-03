package reservasDB.presentacion;

import java.util.Scanner;

import reservasDB.dominio.Reserva;
import reservasDB.servicio.ReservaService;

/**
 * Clase que implementa el menú de la aplicación de gestión de reservas.
 * Proporciona una interfaz de usuario en consola para realizar operaciones CRUD
 * (Crear, Leer, Actualizar, Eliminar) sobre las reservas.
 */

public class MenuAppReservas {
	
	 /**
     * Método principal que ejecuta el menú de la aplicación.
     * Inicializa los recursos necesarios, muestra el menú y maneja las 
     * opciones seleccionadas por el usuario hasta que decide salir.
     * 
     * @throws Exception Si ocurre algún error durante la ejecución de la aplicación
     */
	
	public static void menuAppReservas() {
		Scanner consola = null;
		
		try {
			consola = new Scanner(System.in);
			ReservaService reservaServ = new ReservaService(consola);
			boolean salir = false;
			
			while(!salir) {
				int opcion = mostrarMenu(consola);
				salir = ejecutarOpciones(consola, opcion, reservaServ);
			}
		}catch (Exception e) {
			System.out.println("Error al ejecutar aplicacion. " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (consola != null) {
				consola.close();
			}
		}
	}
	
	/**
     * Muestra el menú principal de la aplicación y captura la opción seleccionada por el usuario.
     * 
     * @param consola Scanner utilizado para leer la entrada del usuario
     * @return int número de la opción seleccionada (0 si la entrada no es válida)
     */
	
	private static int mostrarMenu(Scanner consola) {
		System.out.println();
		System.out.println(" *** Sistema de Reservas *** ");
		System.out.println("1. Mostrar Reservas. ");
		System.out.println("2. Buscar Reserva. ");
		System.out.println("3. Agregar Reserva. ");
		System.out.println("4. Actualizar Reserva. ");
		System.out.println("5. Eliminar Reserva. ");
		System.out.println("6. Salir. ");
		System.out.println("Seleccione una opcion: ");
		
		try {
			String input = consola.nextLine().trim();
			return Integer.parseInt(input);			
		}catch (NumberFormatException e) {
			System.out.println("Error. Debe ingresar un numero valido. ");
			return 0;
		}
	}
	
	/**
     * Ejecuta la opción seleccionada por el usuario en el menú principal.
     * 
     * @param consola Scanner utilizado para leer la entrada del usuario
     * @param opcion Número de la opción seleccionada por el usuario
     * @param reservaServ Servicio de reservas que contiene la lógica de negocio
     * @return boolean Retorna true si el usuario decidió salir de la aplicación, false en caso contrario
     */
	
	private static boolean ejecutarOpciones(Scanner consola, int opcion, ReservaService reservaServ) { 
		switch(opcion) {		
		 case 1:
             mostrarReservas(reservaServ);
             break;
         case 2:
             buscarReserva(reservaServ);
             break;
         case 3:
             agregarReserva(reservaServ);
             break;
         case 4:
             actualizarReserva(reservaServ);
             break;
         case 5:
             eliminarReserva(reservaServ, consola);
             break;
         case 6:
             System.out.println("Saliendo del sistema...");
             return true;
         default:
             System.out.println("Opción incorrecta. Por favor, seleccione una opción válida.");
		}
	 return false;
}
	
	 /**
     * Muestra todas las reservas existentes en el sistema.
     * Si no hay reservas, muestra un mensaje indicándolo.
     * 
     * @param reservaServ Servicio de reservas que contiene la lógica de negocio
     */
	
	private static void mostrarReservas(ReservaService reservaServ) {
        System.out.println("\n*** Listado de Reservas ***");
        if (!reservaServ.mostrarReservas()) {
            System.out.println("No hay reservas para mostrar.");
        }
    }
	
	/**
     * Busca una reserva por su ID y muestra su información.
     * Solicita al usuario que ingrese el ID de la reserva que desea buscar.
     * 
     * @param reservaServ Servicio de reservas que contiene la lógica de negocio
     */
	
    private static void buscarReserva(ReservaService reservaServ) {
        System.out.println("\n*** Buscar Reserva ***");
        Reserva reserva = new Reserva();
        
        if (reservaServ.validarId(reserva) && reservaServ.buscarReserva(reserva)) {
            System.out.println("Reserva encontrada:");
            System.out.println(reserva);
        } else {
            System.out.println("No se encontró la reserva con el ID proporcionado.");
        }
    }
    
    /**
     * Agrega una nueva reserva al sistema.
     * Solicita al usuario que ingrese todos los datos necesarios para crear una reserva.
     * Valida cada campo antes de intentar realizar la inserción.
     * 
     * @param reservaServ Servicio de reservas que contiene la lógica de negocio
     */
    
    private static void agregarReserva(ReservaService reservaServ) {
        System.out.println("\n*** Agregar Reserva ***");
        Reserva reserva = new Reserva();
        
        if (reservaServ.validarNombreReserva(reserva) && 
            reservaServ.validarApellidoReserva(reserva) && 
            reservaServ.validarFechaReserva(reserva) && 
            reservaServ.validarHoraReserva(reserva)) {
            
            if (reservaServ.insertarReserva(reserva)) {
                System.out.println("Reserva agregada exitosamente:");
                System.out.println(reserva);
            } else {
                System.out.println("No se pudo agregar la reserva.");
            }
        }
    }
    
    /**
     * Actualiza los datos de una reserva existente.
     * Solicita al usuario que ingrese el ID de la reserva a actualizar,
     * verifica que exista y luego solicita los nuevos datos para actualizar la reserva.
     * 
     * @param reservaServ Servicio de reservas que contiene la lógica de negocio
     */
    
    private static void actualizarReserva(ReservaService reservaServ) {
        System.out.println("\n*** Actualizar Reserva ***");
        Reserva reserva = new Reserva();
        
        if (reservaServ.validarId(reserva)) {
            // Verificar si la reserva existe antes de modificarla
            if (!reservaServ.buscarReserva(reserva)) {
                System.out.println("No existe una reserva con el ID proporcionado.");
                return;
            }
            
            System.out.println("Datos actuales de la reserva:");
            System.out.println(reserva);
            System.out.println("Ingrese los nuevos datos. ");
            
            if (reservaServ.validarNombreReserva(reserva) && 
                reservaServ.validarApellidoReserva(reserva) && 
                reservaServ.validarFechaReserva(reserva) && 
                reservaServ.validarHoraReserva(reserva)) {
                
                if (reservaServ.modificarReserva(reserva)) {
                    System.out.println("Reserva actualizada exitosamente:");
                    System.out.println(reserva);
                } else {
                    System.out.println("No se pudo actualizar la reserva.");
                }
            }
        }
    }
    
    /**
     * Elimina una reserva existente del sistema.
     * Solicita al usuario que ingrese el ID de la reserva a eliminar,
     * verifica que exista, muestra sus datos y pide confirmación antes de eliminarla.
     * 
     * @param reservaServ Servicio de reservas que contiene la lógica de negocio
     * @param consola Scanner utilizado para leer la entrada del usuario
     */
    
    private static void eliminarReserva(ReservaService reservaServ, Scanner consola) {
        System.out.println("\n*** Eliminar Reserva ***");
        Reserva reserva = new Reserva();
        
        if (reservaServ.validarId(reserva)) {
            // Verificar si la reserva existe antes de eliminarla
            if (reservaServ.buscarReserva(reserva)) {
                System.out.println("Datos de la reserva a eliminar:");
                System.out.println(reserva);
                
                System.out.print("¿Está seguro de eliminar esta reserva? (S/N): ");
                String confirmacion = consola.nextLine().trim().toUpperCase();
                
                if (confirmacion.equals("S")) {
                    if (reservaServ.borrarReserva(reserva)) {
                        System.out.println("Reserva eliminada exitosamente.");
                    } else {
                        System.out.println("No se pudo eliminar la reserva.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("No existe una reserva con el ID proporcionado.");
            }
        }
    }		
}
