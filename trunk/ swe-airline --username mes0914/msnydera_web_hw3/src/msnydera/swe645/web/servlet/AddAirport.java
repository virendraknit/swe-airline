/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.web.servlet;


import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.AirlineUser;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.HeadquartersEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet used to handle the JSP submission of a new airport to add.
 */
public class AddAirport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAirport() {
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
	 *      response) Handles the user input and forwards the user back to the
	 *      PrepareAddAirport servlet. This method will add an 'error' String as
	 *      a request attribute if there are any errors with validation or
	 *      communicating with the remote service.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("/prepareAddAirport");

		AirlineUser user = ResourceUtil.getLoggedInUser(request.getSession());
		
		if(user == null){
			dispatch = request.getRequestDispatcher("jsp/login.jsp");
			request.setAttribute("error", "Please login before accessing the system.");
			
			dispatch.forward(request, response);
			
			return;
		}
		
		String airportCode = request.getParameter("airportCode");
		String errorMessage = validateAirport(airportCode);

		if (errorMessage == null) {
			try {
				addAirport(airportCode);
				request.setAttribute("addedAirportCode", airportCode);

			} catch (ValidationException e) {
				errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
				request.setAttribute("error", errorMessage);
			}

		} else {
			request.setAttribute("error", errorMessage);
		}

		dispatch.forward(request, response);
	}

	/**
	 * Communicates with the remote EJB service to add an airport to the system.
	 * 
	 * @param airport
	 *            Airport to add
	 * @throws ValidationException
	 *             Thrown if there was an error with the validation of the
	 *             provided airport, or if there was an issue in communicating
	 *             with the remote service.
	 */
	private void addAirport(String airport) throws ValidationException {
		try {
			HeadquartersEjbRemote ejbRef = (HeadquartersEjbRemote) ResourceUtil.getInitialContext().lookup(
					Constants.EAR_FILE_NAME + "/HeadquartersEjb/remote");
			ejbRef.createAirport(airport);
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (ValidationException e) {
			throw e;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while attempting to add airport.");
			throw ve;
		}
	}

	/**
	 * Returns null if validation is OK. Otherwise returns an error message if
	 * the provided airport data was bad.
	 * 
	 * @param airportCode
	 *            Airport to validate
	 * @return Null if OK, otherwise returns an error message.
	 */
	private String validateAirport(String airportCode) {
		String errorMessage = null;

		if (airportCode == null || airportCode.trim().equals("")) {
			errorMessage = "The airport code was not provided";
		}

		return errorMessage;
	}
}
