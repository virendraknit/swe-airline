package gmu.swe.ui;


import static org.junit.Assert.assertEquals;

import gmu.swe.domain.SearchFilters;
import gmu.swe.ui.AirlineClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.DateFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AirlineClientTest {
	private AirlineClient airlineClient;
	
	@Before
	public void setUp() throws Exception {
		airlineClient = new AirlineClient("");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShowCurrentSearchOptions_WithEmptyObject(){
		SearchFilters searchFilters = new SearchFilters();
		String detailString = "NONE";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
	
	@Test
	public void testShowCurrentSearchOptions_WithDepartureLocation(){
		SearchFilters searchFilters = new SearchFilters();
		searchFilters.setDepartureLocation("ING");
		String detailString = "Departure (ING)";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
	
	@Test
	public void testShowCurrentSearchOptions_WithDestinationLocation(){
		SearchFilters searchFilters = new SearchFilters();
		searchFilters.setDestinationLocation("FLA");
		String detailString = "Destination (FLA)";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
	
	@Test
	public void testShowCurrentSearchOptions_WithDateOfFlight(){
		SearchFilters searchFilters = new SearchFilters();
		Date date = new Date();
		searchFilters.setDateOfTrip(date);
		String detailString = "Date of Trip (" + DateFormat.getInstance().format(date) + ")";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
	
	@Test
	public void testShowCurrentSearchOptions_WithDepartureAndDateOfFlight(){
		SearchFilters searchFilters = new SearchFilters();
		Date date = new Date();
		searchFilters.setDateOfTrip(date);
		searchFilters.setDepartureLocation("GA");
		String detailString = "Departure (GA), Date of Trip (" + DateFormat.getInstance().format(date) + ")";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
	
	@Test
	public void testShowCurrentSearchOptions_WithDestinationAndDateOfFlight(){
		SearchFilters searchFilters = new SearchFilters();
		Date date = new Date();
		searchFilters.setDateOfTrip(date);
		searchFilters.setDestinationLocation("TEN");
		String detailString = "Destination (TEN), Date of Trip (" + DateFormat.getInstance().format(date) + ")";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
	
	@Test
	public void testShowCurrentSearchOptions_WithDepartureAndDestinationAndDateOfFlight(){
		SearchFilters searchFilters = new SearchFilters();
		Date date = new Date();
		searchFilters.setDateOfTrip(date);
		searchFilters.setDepartureLocation("NC");
		searchFilters.setDestinationLocation("TEN");
		String detailString = "Departure (NC), Destination (TEN), Date of Trip (" + DateFormat.getInstance().format(date) + ")";
		
		String filterDetails = this.airlineClient.showCurrentSearchFilters(searchFilters);
		
		assertEquals(detailString, filterDetails);
	}
}
