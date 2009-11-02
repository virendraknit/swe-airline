/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Domain object used to represent a Flight.
 * 
 */
@Entity
@Table(name="FLIGHT")
@SequenceGenerator(name="FLIGHT_SEQUENCE", sequenceName="IDENTITY")
public class Flight implements Serializable {

	private static final long serialVersionUID = -2909265133578707208L;

	// Unique Id of the flight
	private int id;
	private Date departureDate;
	private Airport departureAirport;
	private Airport destinationAirport;

	// The cost of the flight.
	private double cost;

	// Unique Id of the airplane that is used for the flight
	private Airplane airplane;

	// Number of seats that are available for passengers on the flight.
	private int availableSeats;

	public Flight() {
		//this.airplane = -1;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="FLIGHT_SEQUENCE")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="DEPARTURE_DATE", nullable=false)
	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	@ManyToOne
	@JoinColumn(name="DEPARTURE_AIRPORT_CODE", nullable=false)
	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	@ManyToOne
	@JoinColumn(name="DESTINATION_AIRPORT_CODE", nullable=false)
	public Airport getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	@Column(name="COST", nullable=false)
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@ManyToOne
	@JoinColumn(name="AIRPLANE_ID", nullable=false)
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	@Column(name="AVAILABLE_SEATS", nullable=false)
	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	@Transient
	public String getDisplayDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(this.getDepartureDate());
	}
}
