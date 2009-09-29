package gmu.swe.domain;

import java.io.Serializable;

/**
 * Domain object used to represent an Airplane.
 * 
 * @author mbsnyder
 * 
 */
public class Airplane implements Serializable {

	private static final long serialVersionUID = -1882443873536037244L;

	// Unique Id of the airplane
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
