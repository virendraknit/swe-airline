package gmu.swe.web.servlet;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.ejb.TravelAgentEjbRemote;
import gmu.swe.util.NumberUtils;
import gmu.swe.util.ResourceUtil;
import gmu.swe.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReserveFlight
 */
public class ReserveFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReserveFlight() {
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

		int flightId = Integer.parseInt(request.getParameter("flightId"));
		String numSeats = request.getParameter("numSeats");

		String errorMessage = validateReservation(flightId, numSeats, request);

		if (errorMessage != null) {
			request.setAttribute("error", errorMessage);
			request.setAttribute("flights", request.getSession().getAttribute("savedFlights"));
		} else {
			Reservation reservation;
			try {
				reservation = createReservation(flightId, Integer.parseInt(numSeats));
				
				request.getSession().setAttribute("savedFlights", null);
				dispatch = request.getRequestDispatcher("jsp/reservation.jsp");
				request.setAttribute("reservation", reservation);
				
			} catch (ValidationException e) {
				errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
				
				request.setAttribute("error", errorMessage);
				request.setAttribute("flights", request.getSession().getAttribute("savedFlights"));
			}
			
			
		}

		dispatch.forward(request, response);
	}

	/**
	 * Creates a reservation for on provided flight number, flightId, for the
	 * provided number of seats, numSeats.
	 * 
	 * @param flightId
	 *            Flight number the reservation should be created for.
	 * @param numSeats
	 *            The number of seats the reservation is for.
	 * @return The Reservation created.
	 * @throws ValidationException 
	 */
	private Reservation createReservation(int flightId, int numSeats) throws ValidationException {
		try {
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)ResourceUtil.getInitialContext().lookup("AirlineHw2/TravelAgentEjb/remote");
			return ejbRef.createReservation(flightId, numSeats);
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (ValidationException e) {
			throw e;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during reservation creation.");
			throw ve;
		}
	}
	
	/**
	 * Returns an error message if there is a problem with the reservation
	 * input. Returns null if the reservation information is valid/complete.<br>
	 * <br>
	 * Errors include:<br>
	 * - Number of seats is not a WHOLE number (ex: it is text)<br>
	 * - If the number of seats < 1 <br>
	 * - Flight doesn't have enough seats.
	 * 
	 * @param flightId
	 *            Flight # to reserve
	 * @param numSeats
	 *            Number of seats to reserve
	 * @param request
	 *            HttpServletRequest
	 * @return Any error messages, or null if there are no errors.
	 */
	@SuppressWarnings("unchecked")
	private String validateReservation(int flightId, String numSeats, HttpServletRequest request) {
		String errorMessage = null;

		if (!NumberUtils.isWholeNumber(numSeats)) {
			errorMessage = "'" + numSeats + "' is not a valid number.  Please enter a WHOLE number.";
		} else if (!isValidSeatNumber(numSeats)) {
			errorMessage = "The number of seats to reserve must be > 0.";
		} else if (!flightHasEnoughSeats(flightId, Integer.parseInt(numSeats), (ArrayList<Flight>) request.getSession()
				.getAttribute("savedFlights"))) {

			errorMessage = "Flight #" + flightId + " does not have " + numSeats
					+ " seats available.  Please choose another flight or less seats.";
		}

		return errorMessage;
	}

	/**
	 * Returns true if the flightId given is an actual flight Id that was in the
	 * search results AND if the number of seats to reserve is <= the number of
	 * available seats on the airplane.
	 * 
	 * @param flightId
	 *            Id of the Flight to reserve
	 * @param numSeats
	 *            The number of seats to reserve
	 * @param flights
	 *            The flights from the search results
	 * @return True if the flightId provided was in the search results AND has
	 *         enough seats to reserve.
	 */
	private boolean flightHasEnoughSeats(int flightId, int numSeats, ArrayList<Flight> flights) {
		for (Flight flight : flights) {
			if (flight.getId() == flightId && flight.getAvailableSeats() >= numSeats) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidSeatNumber(String numSeats) {
		if (NumberUtils.isWholeNumber(numSeats)) {
			return Integer.parseInt(numSeats) > 0;
		}
		return false;
	}
	
	
//	private Reservation createReservationTest(int flightId, int numSeats) {
//		Reservation reservation = new Reservation();
//		reservation.setId(12344);
//		reservation.setNumSeats(numSeats);
//		
//		Flight flight = new Flight();
//		flight.setId(222);
//		flight.setAirplaneId(22);
//		flight.setAvailableSeats(180);
//		flight.setCost(125.00);
//		flight.setDepartureAirportCode("IAD");
//		flight.setDestinationAirportCode("NAC");
//		flight.setDepartureDate(new Date());
//		
//		reservation.setFlight(flight);
//		
//		return reservation;
//	}
}
