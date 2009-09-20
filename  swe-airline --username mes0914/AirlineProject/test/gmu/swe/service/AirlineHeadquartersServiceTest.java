package gmu.swe.service;


import gmu.swe.domain.Flight;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AirlineHeadquartersServiceTest {
	private AirlineHeadquartersServiceTest service;
	
	@Before
	public void setUp() throws Exception {
		this.service = new AirlineHeadquartersServiceTest();
	}

	@After
	public void tearDown() throws Exception {
		this.service = null;
	}

	@Test
	public void testCreateFlight(){
//		stmt.setDate(1, new Date(flight.getDepartureDate().getTime()));
//		stmt.setString(2, flight.getDepartureAirportCode().toUpperCase());
//		stmt.setString(3, flight.getDestinationAirportCode().toUpperCase());
//		stmt.setDouble(4, flight.getCost());
//		stmt.setInt(5, flight.getAirplaneId());
//		stmt.setInt(6, flight.getAvailableSeats());
		
		Flight flight = new Flight();
		
	}
}
