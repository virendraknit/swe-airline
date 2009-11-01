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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Domain object used to represent an Airplane.
 * 
 */
//@Entity
//@Table(name="Airplane")
//@SequenceGenerator(name="AIRPLANE_SEQUENCE", sequenceName="IDENTITY")
public class Airplane implements Serializable {

	private static final long serialVersionUID = -1882443873536037244L;

	// Unique Id of the airplane
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY, generator="AIRPLANE_SEQUENCE")
	private int id;
	
	// Number of seats on the airplane.
	private int numSeats;
	
	// The airplane type
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
