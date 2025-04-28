package reservasDB.datos;

import reservasDB.dominio.Reserva;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReservaDAOTest {
	

//						@Test
//						public void testListarReservas() { 
//							ReservaDAO reservaDao = new ReservaDAO();
//							List<Reserva> listar = new ArrayList<>();
//							listar = reservaDao.listarReservas();
//							assertNotNull("La lista no debe ser nula. ", listar);
//							assertFalse("La lista deberia tener reservas. ", listar.isEmpty());
//							listar.forEach(System.out::println);			
//						}

//					@Test
//					public void testAgregarReserva() {
//						ReservaDAO reservaDao = new ReservaDAO();
//						String nombreReserva = "Ana";
//						String apellidoReserva = "Rosales";
//						LocalDate fechaReserva =  LocalDate.of(2025, 4, 20);
//						LocalTime horaReserva = LocalTime.of(21, 00);
//						
//						Reserva nuevaReserva = new Reserva(nombreReserva, apellidoReserva, fechaReserva, horaReserva);
//							boolean insertar = reservaDao.agregarReserva(nuevaReserva);
//							
//							assertTrue("La reserva se ha agregado a la BD. ", insertar);
//						}
	
//					@Test 
//					public void testAgregarReservaNULL() {
//						ReservaDAO reservaDao = new ReservaDAO();
//						String nombreReserva = "";
//						String apellidoReserva = "";
//						LocalDate fechaReserva =  LocalDate.of(0, 0, 0);
//						LocalTime horaReserva = LocalTime.of(22, 00);
//						
//						Reserva nuevaReserva = new Reserva(nombreReserva, apellidoReserva, fechaReserva, horaReserva);
//						boolean insertar = reservaDao.agregarReserva(nuevaReserva);
//							
//						assertNull(nuevaReserva);
//					}

//				@Test 
//				public void testAgregarReservaFechaPasada() {
//					ReservaDAO reservaDao = new ReservaDAO();
//					String nombreReserva = "Laura";
//					String apellidoReserva = "Ramos";
//					LocalDate fechaReserva =  LocalDate.of(2025, 4, 10);
//					LocalTime horaReserva = LocalTime.of(22, 00);
//					
//					Reserva nuevaReserva = new Reserva(nombreReserva, apellidoReserva, fechaReserva, horaReserva);
//					boolean insertar = reservaDao.agregarReserva(nuevaReserva);
//						
//					assertTrue(insertar);
//				}

//					@Test
//					public void testBuscarReservaPorId() {
//						ReservaDAO reservaDao = new ReservaDAO(); //ejecutar prueba de reserva antes de la busqueda
//						int id = 8;
//						boolean buscar = reservaDao.buscarReservaPorId(new Reserva(id));
//						assertTrue(buscar);					 
//					}
			
//						@Test
//						public void testActualizarReserva() {
//							ReservaDAO reservaDao = new ReservaDAO();
//							String nombreReserva = "Carla";
//							String apellidoReserva = "Rosales";
//							LocalDate fechaReserva =  LocalDate.of(2025, 4, 21);
//							LocalTime horaReserva = LocalTime.of(21, 30);
//							int id = 4;
//							Reserva reservaActualizada = new Reserva(nombreReserva, apellidoReserva, fechaReserva, horaReserva);
//							reservaActualizada.setId(id);
//							
//							boolean actualizar = reservaDao.actualizarReserva(reservaActualizada);
//							assertTrue("La reserva se ha actualizado en la BD. ", actualizar);
//						}
//			
//					@Test
//					public void testEliminarReserva() {
//						ReservaDAO reservaDao = new ReservaDAO(); //ejecutar prueba de reserva antes de la busqueda
//						int id = 7;
//						Reserva reserva = new Reserva(id);
//						boolean eliminar = reservaDao.eliminarReserva(reserva);
//						
//						assertTrue("La reserva se ha eliminado de la BD. ", eliminar);
//					}
					
}
