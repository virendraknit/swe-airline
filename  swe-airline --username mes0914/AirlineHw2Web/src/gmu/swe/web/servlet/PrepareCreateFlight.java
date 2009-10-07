package gmu.swe.web.servlet;

import gmu.swe.constant.Constants;
import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.ejb.HeadquartersEjbRemote;
import gmu.swe.util.ResourceUtil;
import gmu.swe.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NamingException;
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
	private PrepareAddAirplane airplaneServlet;
	private PrepareAddAirport airportServlet;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrepareCreateFlight() {
		super();
		
		this.airplaneServlet = new PrepareAddAirplane();
		this.airportServlet = new PrepareAddAirport();
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

		try {
			Collection<Airplane> airplanes = this.airplaneServlet.getExistingAirplanes();
			Collection<String> airports = this.airportServlet.getExistingAirports();
			Collection<Flight> flights = getExistingFlights();

			request.setAttribute("airplanes", airplanes);
			request.setAttribute("airports", airports);
			request.setAttribute("flights", flights);
			request.setAttribute("addedFlight", request.getAttribute("addedFlight"));
			request.setAttribute("error", request.getAttribute("error"));
		} catch (ValidationException e) {
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		}

		dispatch.forward(request, response);
	}


	
	
	private Collection<Flight> getExistingFlights() throws ValidationException {
		try {
			HeadquartersEjbRemote ejbRef = (HeadquartersEjbRemote) ResourceUtil.getInitialContext().lookup(
					Constants.EAR_FILE_NAME + "/HeadquartersEjb/remote");
			return ejbRef.getAllFlights();
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while retrieving all the flights.");
			throw ve;
		}
	}

	private Collection<Flight> getExistingFlightsTest() {
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
