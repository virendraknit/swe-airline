package gmu.swe.web.servlet;

import gmu.swe.domain.Flight;
import gmu.swe.domain.SearchFilters;
import gmu.swe.util.DateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FlightSearch
 */
public class FlightSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FlightSearch() {
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
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/searchResults.jsp");

		SearchFilters searchFilters;
		try {
			searchFilters = getSearchFilters(request);
			String errorMessage = validateFilters(searchFilters);

			if (errorMessage == null) {
				ArrayList<Flight> flights = getFlights();

				request.getSession().setAttribute("savedFlights", flights);
				request.setAttribute("flights", flights);
			} else {
				dispatch = request.getRequestDispatcher("/prepareSearch");
				request.setAttribute("error", errorMessage);
			}
		} catch (ParseException e) {
			e.printStackTrace();

			dispatch = request.getRequestDispatcher("/prepareSearch");
			String errorMessage = "Please provide a date in the format MM/dd/yyyy.";
			request.setAttribute("error", errorMessage);
		}
		
		dispatch.forward(request, response);
	}

	private ArrayList<Flight> getFlights() {
		ArrayList<Flight> flights = new ArrayList<Flight>();

		Flight flight = new Flight();
		flight.setId(111);
		flight.setAirplaneId(11);
		flight.setAvailableSeats(200);
		flight.setCost(150.00);
		flight.setDepartureAirportCode("BWI");
		flight.setDestinationAirportCode("WAS");
		flight.setDepartureDate(new Date());

		flights.add(flight);

		flight = new Flight();
		flight.setId(222);
		flight.setAirplaneId(22);
		flight.setAvailableSeats(180);
		flight.setCost(125.00);
		flight.setDepartureAirportCode("IAD");
		flight.setDestinationAirportCode("NAC");
		flight.setDepartureDate(new Date());
		
		flights.add(flight);
		
		return flights;
	}

	private String validateFilters(SearchFilters searchFilters) {
		String errorMessage = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		if (searchFilters.isAllEmpty()) {
			System.out.println("");
			System.out
					.println("* Error: Running a search requires the Departure and Destination, and/or Date to be set.");
			errorMessage = "Running a search requires the Departure and Destination, and/or Date to be set.";
		}else if(searchFilters.getDateOfTrip() != null && !DateUtil.isTodayOrLater(searchFilters.getDateOfTrip())){
			errorMessage = "Please enter the Date of the Trip that is today or later.";
		}
		return errorMessage;
	}

	private SearchFilters getSearchFilters(HttpServletRequest request) throws ParseException {
		SearchFilters searchFilters = new SearchFilters();
		
		System.out.println("Depart Code: " + (String) request.getParameter("departureAirport"));
		System.out.println("Dest Code: " + (String) request.getParameter("destinationAirport"));
		
		
		searchFilters.setDepartureLocation((String) request.getParameter("departureAirport"));
		searchFilters.setDestinationLocation((String) request.getParameter("destinationAirport"));
		searchFilters.setDateOfTrip(getDateOfTrip(request));

		return searchFilters;
	}

	private Date getDateOfTrip(HttpServletRequest request) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String sDateOfTrip = (String) request.getParameter("flightDate");
		Date dateOfTrip = null;
		try {
			if (sDateOfTrip != null && !sDateOfTrip.trim().equals("")) {
				dateOfTrip = sdf.parse(sDateOfTrip);
			}
			return dateOfTrip;
		} catch (ParseException e) {
			System.out.print("'" + sDateOfTrip + "' is an invalid date.");
			throw e;
		}
	}
}
