package gmu.swe.domain;

import java.io.Serializable;
import java.util.Date;

public class SearchFilters implements Serializable {
	private static final long serialVersionUID = -7121710518476582087L;
	
	private String departureLocation;
	private String destinationLocation;
	private Date dateOfTrip;

	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public Date getDateOfTrip() {
		return dateOfTrip;
	}

	public void setDateOfTrip(Date dateOfTrip) {
		this.dateOfTrip = dateOfTrip;
	}

	public boolean isAllNull() {
		return this.departureLocation == null
				&& this.destinationLocation == null && this.dateOfTrip == null;
	}
	
	public void nullOutValues(){
		this.departureLocation = null;
		this.destinationLocation = null;
		this.dateOfTrip = null;
	}
}
