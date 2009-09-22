package gmu.swe.domain;

import java.io.Serializable;

public class Airplane implements Serializable {

	private static final long serialVersionUID = -1882443873536037244L;

	private int id;
	private int numSeats;
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
