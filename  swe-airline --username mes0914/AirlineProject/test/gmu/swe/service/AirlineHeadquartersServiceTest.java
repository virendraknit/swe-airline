package gmu.swe.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gmu.swe.domain.Flight;
import gmu.swe.util.DateUtil;

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
	public void testCreateFlight() throws ParseException{
//		stmt.setDate(1, new Date(flight.getDepartureDate().getTime()));
//		stmt.setString(2, flight.getDepartureAirportCode().toUpperCase());
//		stmt.setString(3, flight.getDestinationAirportCode().toUpperCase());
//		stmt.setDouble(4, flight.getCost());
//		stmt.setInt(5, flight.getAirplaneId());
//		stmt.setInt(6, flight.getAvailableSeats());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date dateOfTrip = sdf.parse("09/19/2009");
//			System.out.println(dateOfTrip);
//			System.out.println(new Date());
//			System.out.println(dateOfTrip.before(new Date()));
		
//			System.out.println(dateOfTrip.after(new Date()));
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(dateOfTrip);
//			System.out.println(cal2.get(Calendar.YEAR) == cal.get(Calendar.YEAR));
//			System.out.println(cal2.get(Calendar.MONTH) == cal.get(Calendar.MONTH));
//			System.out.println(cal2.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH));
			
			System.out.println(DateUtil.isTodayOrLater(dateOfTrip));
	}
}
