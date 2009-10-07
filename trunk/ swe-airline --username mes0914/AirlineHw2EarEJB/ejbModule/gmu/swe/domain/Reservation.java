/*
 * Created by: Matt Snyder
 */
package gmu.swe.domain;

import java.io.Serializable;

/**
 * Domain object used to represent a reservation.
 * 
 */
public class Reservation implements Serializable {

	private static final long serialVersionUID = -1736327910587589116L;

	// Unique Id of the reservation (i.e. the Reservation number)
	private int id;
	
	// Number of seats the reservation is for
	private int numSeats;
	
	// The flight the reservation is for
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
	
	public double getTotalCost(){
		return flight.getCost() * (double)numSeats;
	}
}
