package msnydera.swe645.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="Airport")
public class Airport implements Serializable{

	private static final long serialVersionUID = -7559503977982953503L;

	private String airportCode;

	@Id
	@Column(name="code",nullable=false)
	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
}
