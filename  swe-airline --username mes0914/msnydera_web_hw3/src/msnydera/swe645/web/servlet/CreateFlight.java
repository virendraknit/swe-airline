/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.AirlineUser;
import msnydera.swe645.domain.Airplane;
import msnydera.swe645.domain.Airport;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.HeadquartersEjbRemote;
import msnydera.swe645.util.NumberUtils;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet providing functionality to create a flight in the system.
 */
public class CreateFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateFlight() {
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
	 *      response) Handles the user input for information required to create
	 *      a flight in the system. This method will forward the user to the
	 *      PrepareCreateFlight page when complete (from either a successful
	 *      transaction or if an error occurs).
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("/prepareCreateFlight");

		AirlineUser user = ResourceUtil.getLoggedInUser(request.getSession());

		if (user == null) {
			dispatch = request.getRequestDispatcher("jsp/login.jsp");
			request.setAttribute("error", "Please login before accessing the system.");

			dispatch.forward(request, response);

			return;
		}

		try {
			Flight flight = getFlight(request);

			int newFlightNum = createFlight(flight, user);
			flight.setId(newFlightNum);

			request.setAttribute("addedFlight", flight);

		} catch (ValidationException e) {

			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);

		} catch (ParseException e) {
			e.printStackTrace();

			String errorMessage = "Please provide a date in the format MM/dd/yyyy.";
			request.setAttribute("error", errorMessage);
		} catch (LoginException e) {
			dispatch = request.getRequestDispatcher("jsp/headquartersMenu.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");

		} catch (Exception e) {
			dispatch = request.getRequestDispatcher("jsp/headquartersMenu.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");
		}

		dispatch.forward(request, response);
	}

	/**
	 * Communicate with the remote EJB service to create a flight in the system.
	 * 
	 * @param flight
	 *            Flight to create
	 * @param user
	 *            AirlineUser to use
	 * @return Flight # of the flight created.
	 * @throws ValidationException
	 *             Thrown if a validation error occurs or if there is a problem
	 *             with communicating with the remote EJB service.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws LoginException
	 *             Thrown if a problem occurs when logging the user in.
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private int createFlight(Flight flight, AirlineUser user) throws ValidationException, LoginException, Exception {
		try {
			// HeadquartersEjbRemote ejbRef = (HeadquartersEjbRemote)
			// ResourceUtil.getInitialContext().lookup(
			// Constants.EAR_FILE_NAME + "/HeadquartersEjb/remote");
			HeadquartersEjbRemote ejbRef = (HeadquartersEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
					Constants.EAR_FILE_NAME + "/HeadquartersEjb/remote");
			return ejbRef.createFlight(flight);
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (ValidationException e) {
			throw e;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while attempting to add airplane.");
			throw ve;
		}
	}

	/**
	 * Binds the request parameters to a Flight object, and returns that object.
	 * 
	 * @param request
	 *            Request containing the information to bind.
	 * @return Flight object bound with the user input for the flight
	 *         information.
	 * @throws ParseException
	 *             Thrown if the flight date in the request is in a bad/invalid
	 *             format.
	 * @throws ValidationException
	 */
	private Flight getFlight(HttpServletRequest request) throws ParseException, ValidationException {
		Flight flight = new Flight();

		Airport airport = new Airport();
		airport.setAirportCode(request.getParameter("departureAirport"));
		flight.setDepartureAirport(airport);

		airport = new Airport();
		airport.setAirportCode(request.getParameter("destinationAirport"));
		flight.setDestinationAirport(airport);

		flight.setDepartureDate(getDateOfTrip(request));

		if (!NumberUtils.isValidCurrency(request.getParameter("cost"))) {
			ValidationException validationException = new ValidationException();
			validationException.addErrorMessage("Please enter a valid cost for the flight (ex: 150.00)");
			throw validationException;
		}

		flight.setCost(Double.parseDouble(request.getParameter("cost")));

		Airplane airplane = new Airplane();
		airplane.setId(Integer.parseInt(request.getParameter("airplaneId")));

		flight.setAirplane(airplane);

		return flight;
	}

	/**
	 * Returns a Date object that was set in the 'flightDate' parameter in the
	 * request.
	 * 
	 * @param request
	 *            Request containing the parameter.
	 * @return Date object of the date in the 'flightDate' parameter.
	 * @throws ParseException
	 *             Thrown if the flight date in the request is in a bad/invalid
	 *             format.
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
