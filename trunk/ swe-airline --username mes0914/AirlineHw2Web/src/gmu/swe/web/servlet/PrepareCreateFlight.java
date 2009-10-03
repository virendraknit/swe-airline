package gmu.swe.web.servlet;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrepareCreateFlight
 */
public class PrepareCreateFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrepareCreateFlight() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/createFlight.jsp");

		Collection<Airplane> airplanes = getExistingAirplanes();
		Collection<String> airports = getExistingAirports();
		Collection<Flight> flights = getExistingFlights();

		request.setAttribute("airplanes", airplanes);
		request.setAttribute("airports", airports);
		request.setAttribute("flights", flights);
		request.setAttribute("addedFlight", request.getAttribute("addedFlight"));
		request.setAttribute("error", request.getAttribute("error"));

		dispatch.forward(request, response);
	}

	private Collection<Airplane> getExistingAirplanes() {
		Collection<Airplane> airplanes = new ArrayList<Airplane>();

		Airplane airplane = new Airplane();
		airplane.setId(3);
		airplane.setType("DC-10");
		airplane.setNumSeats(150);
		airplanes.add(airplane);

		airplane = new Airplane();
		airplane.setId(1);
		airplane.setType("737");
		airplane.setNumSeats(180);
		airplanes.add(airplane);

		airplane = new Airplane();
		airplane.setId(2);
		airplane.setType("747");
		airplane.setNumSeats(200);
		airplanes.add(airplane);

		airplane = new Airplane();
		airplane.setId(4);
		airplane.setType("F-14");
		airplane.setNumSeats(2);
		airplanes.add(airplane);

		return airplanes;
	}

	private Collection<String> getExistingAirports() {
		Collection<String> airports = new ArrayList<String>();

		airports.add("BWI");
		airports.add("IAD");
		airports.add("WAS");

		return airports;
	}

	private Collection<Flight> getExistingFlights() {
		Collection<Flight> flights = new ArrayList<Flight>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			String sDate = "10/25/2009";

			Flight flight = new Flight();
			flight.setId(5);
			flight.setDepartureDate(sdf.parse(sDate));
			flight.setDestinationAirportCode("DCA");
			flight.setDepartureAirportCode("BWI");
			flight.setCost(240.00);
			flight.setAirplaneId(0);
			flight.setAvailableSeats(200);
			flights.add(flight);
			
			flight = new Flight();
			flight.setId(2);
			flight.setDepartureDate(sdf.parse(sDate));
			flight.setDestinationAirportCode("IAD");
			flight.setDepartureAirportCode("WAS");
			flight.setCost(180.00);
			flight.setAirplaneId(3);
			flight.setAvailableSeats(190);
			flights.add(flight);
			
			flight = new Flight();
			flight.setDepartureDate(sdf.parse(sDate));
			flight.setDestinationAirportCode("XYZ");
			flight.setDepartureAirportCode("HAW");
			flight.setCost(510.00);
			flight.setAirplaneId(5);
			flight.setAvailableSeats(280);
			flights.add(flight);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return flights;
	}
}
