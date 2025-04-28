package reservasDB.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reserva implements Serializable {
	private int id;
	private String nombreReserva;
	private String apellidoReserva;
	private LocalDate fechaReserva;
	private LocalTime horaReserva;	
	
	public Reserva() {
		//Constructor vacio
	}
	
	public Reserva(int id) {
		this.id = id;
	}
	
	public Reserva(String nombreCliente, String apellidoCliente, LocalDate fechaReserva, LocalTime horaReserva) {
		this();
		this.nombreReserva = nombreReserva;
		this.apellidoReserva = apellidoReserva;
		this.fechaReserva = fechaReserva;
		this.horaReserva = horaReserva;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombreReserva() {
		return nombreReserva;
	}

	public void setNombreReserva(String nombreReserva) {
		this.nombreReserva = nombreReserva;
	}

	public String getApellidoReserva() {
		return apellidoReserva;
	}

	public void setApellidoReserva(String apellidoReserva) {
		this.apellidoReserva = apellidoReserva;
	}
	
	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public LocalTime getHoraReserva() {
		return horaReserva;
	}

	public void setHoraReserva(LocalTime hora) {
		this.horaReserva = hora;
	}
	
	public String escribirReserva() {
		return id + "," + nombreReserva + "," + apellidoReserva + "," + fechaReserva + "," + horaReserva;
	}
	
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", nombreReserva=" + nombreReserva + ", apellidoReserva="
				+ apellidoReserva + ", fechaReserva=" + fechaReserva + ", horaReserva=" + horaReserva + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidoReserva, fechaReserva, horaReserva, id, nombreReserva);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(apellidoReserva, other.apellidoReserva) && Objects.equals(fechaReserva, other.fechaReserva)
				&& Objects.equals(horaReserva, other.horaReserva) && id == other.id
				&& Objects.equals(nombreReserva, other.nombreReserva);
	}
}
