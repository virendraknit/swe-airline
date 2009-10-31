/*
 * Created by: Matt Snyder
 */

package msnydera.swe645.web.servlet;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.util.DateUtil;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet providing the functionality of searching for flights.
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
	 *      response) Handles the user's input for the searching of a flight.
	 *      This method will forward the user back to the searchResults.jsp
	 *      page. If an error occurs, the message will be put into an 'error'
	 *      attribute field name.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/searchResults.jsp");

		SearchFilters searchFilters;
		try {
			searchFilters = getSearchFilters(request);
			String errorMessage = validateFilters(searchFilters);

			if (errorMessage == null) {
				Collection<Flight> flights = getFlights(searchFilters);

				if (flights == null || flights.size() == 0) {
					errorMessage = "Your search found no flights.  Please run a different search.";
					dispatch = request.getRequestDispatcher("/prepareSearch");
					request.setAttribute("error", errorMessage);
				} else {
					request.getSession().setAttribute("savedFlights", flights);
					request.setAttribute("flights", flights);
				}
			} else {
				dispatch = request.getRequestDispatcher("/prepareSearch");
				request.setAttribute("error", errorMessage);
			}
		} catch (ParseException e) {
			e.printStackTrace();

			dispatch = request.getRequestDispatcher("/prepareSearch");
			String errorMessage = "Please provide a date in the format MM/dd/yyyy.";
			request.setAttribute("error", errorMessage);
		} catch (ValidationException e) {
			dispatch = request.getRequestDispatcher("/prepareSearch");
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());

			request.setAttribute("error", errorMessage);
		}

		dispatch.forward(request, response);
	}

	/**
	 * Communicates wit the remote EJB service to search for flights based on
	 * the provided SearchFilters object.
	 * 
	 * @param searchFilters
	 *            Filters used to limit the search results
	 * @return Collection of flights that match the search filters.
	 * @throws ValidationException
	 *             Thrown if there is a validation error or if there is a
	 *             problem in communicating with the EJB.
	 */
	private Collection<Flight> getFlights(SearchFilters searchFilters) throws ValidationException {

		try {
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getInitialContext().lookup(
					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");

			return ejbRef.search(searchFilters);
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (ValidationException e) {
			throw e;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during search.");
			throw ve;
		}
	}

	/**
	 * Validates the SearchFilters object.
	 * 
	 * @param searchFilters
	 *            Object to validate
	 * @return Returns null if there are no validation errors, otherwise the
	 *         returned string will contain an error message.
	 */
	private String validateFilters(SearchFilters searchFilters) {
		String errorMessage = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		if (searchFilters.isAllEmpty()) {
			System.out.println("");
			System.out
					.println("* Error: Running a search requires the Departure and Destination, and/or Date to be set.");
			errorMessage = "Running a search requires the Departure and Destination, and/or Date to be set.";
		} else if (searchFilters.getDateOfTrip() != null && !DateUtil.isTodayOrLater(searchFilters.getDateOfTrip())) {
			errorMessage = "Please enter the Date of the Trip that is today or later.";
		}
		return errorMessage;
	}

	/**
	 * Binds the Request object to a SearchFilters object and returns that bound
	 * object.
	 * 
	 * @param request
	 *            Object containing the information to bind to a SearchFilters
	 *            object.
	 * @return SearchFilters object with the bound information from the request.
	 * @throws ParseException
	 *             Thrown if there is a problem with the format of the provided
	 *             date in the request.
	 */
	private SearchFilters getSearchFilters(HttpServletRequest request) throws ParseException {
		SearchFilters searchFilters = new SearchFilters();

		searchFilters.setDepartureLocation((String) request.getParameter("departureAirport"));
		searchFilters.setDestinationLocation((String) request.getParameter("destinationAirport"));
		searchFilters.setDateOfTrip(getDateOfTrip(request));

		return searchFilters;
	}

	/**
	 * Returns a Date object of a date that was in the request under the
	 * parameter 'flightDate'.
	 * 
	 * @param request
	 *            Request object containing the flight parameter.
	 * @return Date of the flight parameter
	 * @throws ParseException
	 *             Thrown if the flight parameter is in an invalid format.
	 */
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
