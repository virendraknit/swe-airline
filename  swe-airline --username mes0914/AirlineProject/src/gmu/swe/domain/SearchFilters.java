package gmu.swe.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Domain object used as a filter when searching for a flight.
 * 
 * @author mbsnyder
 * 
 */
public class SearchFilters implements Serializable {
	private static final long serialVersionUID = -7121710518476582087L;

	private String departureLocation;
	private String destinationLocation;
	private Date dateOfTrip;

	public String getDepartureLocation() {
		return departureLocation == null ? null : departureLocation.toUpperCase();
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation == null ? null : departureLocation.toUpperCase();
	}

	public String getDestinationLocation() {
		return destinationLocation == null ? null : destinationLocation.toUpperCase();
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation == null ? null : destinationLocation.toUpperCase();
	}

	public Date getDateOfTrip() {
		return dateOfTrip;
	}

	public void setDateOfTrip(Date dateOfTrip) {
		this.dateOfTrip = dateOfTrip;
	}

	public boolean isAllNull() {
		return this.departureLocation == null && this.destinationLocation == null && this.dateOfTrip == null;
	}

	public void nullOutValues() {
		this.departureLocation = null;
		this.destinationLocation = null;
		this.dateOfTrip = null;
	}
}
