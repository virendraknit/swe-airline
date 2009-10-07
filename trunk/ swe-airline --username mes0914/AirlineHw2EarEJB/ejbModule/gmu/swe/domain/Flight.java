/*
 * Created by: Matt Snyder
 */
package gmu.swe.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Domain object used to represent a Flight.
 * 
 */
public class Flight implements Serializable {

	private static final long serialVersionUID = -2909265133578707208L;

	// Unique Id of the flight
	private int id;
	private Date departureDate;
	private String departureAirportCode;
	private String destinationAirportCode;

	// The cost of the flight.
	private double cost;

	// Unique Id of the airplane that is used for the flight
	private int airplaneId;

	// Number of seats that are available for passengers on the flight.
	private int availableSeats;

	public Flight() {
		this.airplaneId = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}

	public void setDestinationAirportCode(String destinationAirportCode) {
		this.destinationAirportCode = destinationAirportCode;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(int airplaneId) {
		this.airplaneId = airplaneId;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	public String getDisplayDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(this.getDepartureDate());
	}
}
