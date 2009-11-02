/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Domain object used to represent a reservation.
 * 
 */
@Entity
@Table(name="RESERVATION")
@SequenceGenerator(name="RESERVATION_SEQUENCE", sequenceName="IDENTITY")
public class Reservation implements Serializable {

	private static final long serialVersionUID = -1736327910587589116L;

	// Unique Id of the reservation (i.e. the Reservation number)
	private int id;
	
	// Number of seats the reservation is for
	private int numSeats;
	
	// The flight the reservation is for
	private Flight flight;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="RESERVATION_SEQUENCE")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="NUM_SEATS", nullable=false)
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	
	@ManyToOne
	@JoinColumn(name="FLIGHT_ID", nullable=false)
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	@Transient
	public double getTotalCost(){
		if(flight != null){
			return flight.getCost() * (double)numSeats;
		}
		
		return 0;
	}
}
