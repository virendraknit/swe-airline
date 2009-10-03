package gmu.swe.web.servlet;

import gmu.swe.domain.Flight;
import gmu.swe.exception.ValidationException;
import gmu.swe.util.NumberUtils;

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
 * Servlet implementation class CreateFlight
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
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("/prepareCreateFlight");
		

		try {
			Flight flight = getFlight(request);
			
			flight = createFlight(flight);
			request.setAttribute("addedFlight", flight);

		} catch (ValidationException e) {
			ArrayList<String> errorMessages = e.getErrorMessages();
			String errorMessage = new String();
			
			for (String error : errorMessages) {
				if(errorMessage.length() > 0){
					errorMessage += "\n";
				}
				errorMessage += error;
			}
			
			request.setAttribute("error", errorMessage);
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();

			String errorMessage = "Please provide a date in the format MM/dd/yyyy.";
			request.setAttribute("error", errorMessage);
		}

		dispatch.forward(request, response);
	}

	private Flight createFlight(Flight flight) throws ValidationException {
		return flight;
	}

	private Flight getFlight(HttpServletRequest request) throws ParseException, ValidationException {
		Flight flight = new Flight();
		flight.setDepartureAirportCode(request.getParameter("departureAirport"));
		flight.setDestinationAirportCode(request.getParameter("destinationAirport"));
		flight.setDepartureDate(getDateOfTrip(request));
		
		if(!NumberUtils.isValidCurrency(request.getParameter("cost"))){
			ValidationException validationException = new ValidationException();
			validationException.addErrorMessage("Please enter a valid cost for the flight (ex: 150.00)");
			throw validationException;
		}
		
		flight.setCost(Integer.parseInt(request.getParameter("cost")));
		flight.setAirplaneId(Integer.parseInt(request.getParameter("airplaneId")));

		return flight;
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
