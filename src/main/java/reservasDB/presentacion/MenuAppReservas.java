package reservasDB.presentacion;

import java.util.Scanner;

import reservasDB.dominio.Reserva;
import reservasDB.servicio.ReservaService;

public class MenuAppReservas {
	
	   /**
     * Método principal que ejecuta el menú de la aplicación
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
     * Ejecuta la opción seleccionada por el usuario
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
     * Muestra todas las reservas existentes
     */
	
	private static void mostrarReservas(ReservaService reservaServ) {
        System.out.println("\n*** Listado de Reservas ***");
        if (!reservaServ.mostrarReservas()) {
            System.out.println("No hay reservas para mostrar.");
        }
    }
	
	 /**
     * Busca una reserva por su ID
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
     * Agrega una nueva reserva
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
     * Actualiza una reserva existente
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
     * Elimina una reserva existente
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
