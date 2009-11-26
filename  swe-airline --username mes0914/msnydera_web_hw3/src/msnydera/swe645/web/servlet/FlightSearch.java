/*
 * Created by: Matt Snyder
 */

package msnydera.swe645.web.servlet;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.Airplane;
import msnydera.swe645.domain.Airport;
import msnydera.swe645.domain.Customer;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjb;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.service.ejb.TravelAgentEjbServiceLocator;
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

		String serviceToUse = request.getParameter("searchType");

		/*
		 * This is where we determine whether to use the EJB or the Web Service
		 * to run the search.
		 */
		if (serviceToUse.trim().equalsIgnoreCase("ejb")) {
			dispatch = searchWithEjb(request, dispatch);
		} else {
			dispatch = searchWithWebService(request, dispatch);
		}

		dispatch.forward(request, response);
	}

	/**
	 * This method runs a search against the EJB services.
	 * 
	 * @param request
	 *            Request that contains the search information
	 * @param dispatch
	 *            Dispatcher to use to to redirect the user.
	 * @return RequestDispatcher to forward.
	 */
	private RequestDispatcher searchWithEjb(HttpServletRequest request, RequestDispatcher dispatch) {
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

					Collection<Customer> customers = getCustomers();
					request.getSession().setAttribute("customers", customers);
					request.setAttribute("customers", customers);
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
		} catch (Exception e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");
		}
		return dispatch;
	}

	/**
	 * This method runs a search against the Web Services.
	 * 
	 * @param request
	 *            Request that contains the search information
	 * @param dispatch
	 *            Dispatcher to use to to redirect the user.
	 * @return RequestDispatcher to forward.
	 */
	private RequestDispatcher searchWithWebService(HttpServletRequest request, RequestDispatcher dispatch) {
		try {
			msnydera.swe645.service.ejb.SearchFilters searchFilters = getWebServiceSearchFilters(request);

			String errorMessage = validateWebServiceFilters(searchFilters);

			if (errorMessage == null) {
				msnydera.swe645.service.ejb.Flight[] webServiceFlights = getWebServiceFlights(searchFilters);

				Collection<Flight> flights = convertFlights(webServiceFlights);

				if (flights == null || flights.size() == 0) {
					errorMessage = "Your search found no flights.  Please run a different search.";
					dispatch = request.getRequestDispatcher("/prepareSearch");
					request.setAttribute("error", errorMessage);
				} else {
					request.getSession().setAttribute("savedFlights", flights);
					request.setAttribute("flights", flights);

					Collection<Customer> customers = getCustomers();
					request.getSession().setAttribute("customers", customers);
					request.setAttribute("customers", customers);
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
		} catch (Exception e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");
		}
		return dispatch;
	}

	/**
	 * Given an array of Web Service Flight objects, this method will return a
	 * Collection of Flight objects (i.e. the domain object that the JSPs
	 * expect).
	 * 
	 * @param webServiceFlights
	 *            Array of Web Service Flight objects.
	 * @return Collection of our domain Flight objects.
	 */
	private Collection<Flight> convertFlights(msnydera.swe645.service.ejb.Flight[] webServiceFlights) {
		Collection<Flight> flights = null;

		if (webServiceFlights != null && webServiceFlights.length > 0) {
			flights = new ArrayList<Flight>();

			for (int i = 0; i < webServiceFlights.length; i++) {
				msnydera.swe645.service.ejb.Flight wsFlight = webServiceFlights[i];
				Flight flight = new Flight();
				flight.setAirplane(convertAirplane(wsFlight.getAirplane()));
				flight.setAvailableSeats(wsFlight.getAvailableSeats());
				flight.setCost(wsFlight.getCost());
				flight.setDepartureAirport(convertAirport(wsFlight.getDepartureAirport()));
				flight.setDepartureDate(wsFlight.getDepartureDate().getTime());
				flight.setDestinationAirport(convertAirport(wsFlight.getDestinationAirport()));
				flight.setId(wsFlight.getId());
			}
		}

		return flights;
	}

	/**
	 * Given a Web Service Airport object, this method will return our domain
	 * Airport object.
	 * 
	 * @param wsAirport
	 *            Web Service Airport object.
	 * @return Our domain Airport object.
	 */
	private Airport convertAirport(msnydera.swe645.service.ejb.Airport wsAirport) {
		Airport airport = new Airport();
		airport.setAirportCode(wsAirport.getAirportCode());

		return airport;
	}

	/**
	 * Given a Web Service Airplane object, this method will return our domain
	 * Airplane object.
	 * 
	 * @param wsAirplane
	 *            Web Service Airplane object.
	 * @return Our domain Airplane object.
	 */
	private Airplane convertAirplane(msnydera.swe645.service.ejb.Airplane wsAirplane) {
		Airplane airplane = new Airplane();
		airplane.setId(wsAirplane.getId());
		airplane.setNumSeats(wsAirplane.getNumSeats());
		airplane.setType(wsAirplane.getType());

		return airplane;
	}

	/**
	 * Communicates with the remote EJB service to search for flights based on
	 * the provided SearchFilters object.
	 * 
	 * @param searchFilters
	 *            Filters used to limit the search results
	 * @return Collection of flights that match the search filters.
	 * @throws ValidationException
	 *             Thrown if there is a validation error or if there is a
	 *             problem in communicating with the EJB.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private Collection<Flight> getFlights(SearchFilters searchFilters) throws ValidationException, Exception {

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
	 * Communicates with the Web Service to search for flights based on the
	 * provided Web Service'sSearchFilters object.
	 * 
	 * @param searchFilters
	 *            Filters used to limit the search results
	 * @return Flight[] of Web Service flights that match the searchFilters.
	 * @throws ValidationException
	 *             Thrown if there is a validation error or if there is a
	 *             problem in communicating with the Web Service.
	 */
	private msnydera.swe645.service.ejb.Flight[] getWebServiceFlights(
			msnydera.swe645.service.ejb.SearchFilters searchFilters) throws ValidationException {

		try {
			TravelAgentEjbServiceLocator service = new TravelAgentEjbServiceLocator();

			TravelAgentEjb ejbRef = service.getTravelAgentEjbPort();

			msnydera.swe645.service.ejb.Flight[] flights = ejbRef.search(searchFilters);

			if (flights == null) {
				System.out.println("Flights was null");
			} else {
				System.out.println("Number of Flights: " + flights.length);
			}

			return flights;
			
		} catch (ServiceException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Error occured while getting a Web Service reference.");
			throw ve;
		} catch (msnydera.swe645.service.ejb.ValidationException e) {
			e.printStackTrace();
			ValidationException ve = covertException(e);

			throw ve;
			
		} catch (msnydera.swe645.service.ejb.DataAccessException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during search.");
			throw ve;
		} catch (RemoteException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during search.");
			throw ve;
		}
	}

	/**
	 * Utility method used to covert a Web Service ValidationException into our
	 * exptected type.
	 * 
	 * @param e
	 *            Exception to covert
	 * @return Our expected ValidationException type.
	 */
	private ValidationException covertException(msnydera.swe645.service.ejb.ValidationException e) {
		ValidationException ve = new ValidationException();
		
		String[] errorMessages = (String[])e.getErrorMessages();
		
		for (int i = 0; i < errorMessages.length; i++) {
			ve.addErrorMessage(errorMessages[i]);
		}

		return ve;
	}

	/**
	 * Communicates with the remote EJB service to retrieve all of the
	 * customers.
	 * 
	 * @return Collection of customers.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private Collection<Customer> getCustomers() throws ValidationException, Exception {
		try {
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getInitialContext().lookup(
					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			// TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)
			// ResourceUtil.getLoggedInContext(user).lookup(
			// Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");

			return ejbRef.getAllCustomers();
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while retrieving all the customers.");
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
	 * Validates the Web Service's SearchFilters object.
	 * 
	 * @param searchFilters
	 *            Object to validate
	 * @return Returns null if there are no validation errors, otherwise the
	 *         returned string will contain an error message.
	 */
	private String validateWebServiceFilters(msnydera.swe645.service.ejb.SearchFilters searchFilters) {
		String errorMessage = null;

		if ((searchFilters.getDepartureLocation() == null || searchFilters.getDepartureLocation().trim().equals(""))
				&& (searchFilters.getDestinationLocation() == null || searchFilters.getDestinationLocation().trim()
						.equals("")) && searchFilters.getDateOfTrip() == null) {

			System.out.println("");
			System.out
					.println("* Error: Running a search requires the Departure and Destination, and/or Date to be set.");
			errorMessage = "Running a search requires the Departure and Destination, and/or Date to be set.";
		} else if (searchFilters.getDateOfTrip() != null
				&& !DateUtil.isTodayOrLater(searchFilters.getDateOfTrip().getTime())) {
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
	 * Binds the Request object to the Web Service's SearchFilters object and
	 * returns that bound object.
	 * 
	 * @param request
	 *            Object containing the information to bind to a SearchFilters
	 *            object.
	 * @return Web Service's SearchFilters object with the bound information
	 *         from the request.
	 * @throws ParseException
	 *             Thrown if there is a problem with the format of the provided
	 *             date in the request.
	 */
	private msnydera.swe645.service.ejb.SearchFilters getWebServiceSearchFilters(HttpServletRequest request)
			throws ParseException {
		msnydera.swe645.service.ejb.SearchFilters searchFilters = new msnydera.swe645.service.ejb.SearchFilters();

		searchFilters.setDepartureLocation((String) request.getParameter("departureAirport"));
		searchFilters.setDestinationLocation((String) request.getParameter("destinationAirport"));

		Calendar cal = Calendar.getInstance();
		cal.setTime(getDateOfTrip(request));

		searchFilters.setDateOfTrip(cal);

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
