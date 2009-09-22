package gmu.swe.domain;

import java.io.Serializable;

public class Reservation implements Serializable {

	private static final long serialVersionUID = -1736327910587589116L;

	private int id;
	private int numSeats;
	private Flight flight;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
}
