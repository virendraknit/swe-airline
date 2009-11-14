/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.web.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.AirlineUser;
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet used to prepare the request object for the page to view all
 * reservations.
 */
public class ViewReservations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewReservations() {
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
	 *      response) Adds a collection of all the reservations in the system to
	 *      the Request object.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/viewReservations.jsp");

		AirlineUser user = ResourceUtil.getLoggedInUser(request.getSession());

		if (user == null) {
			dispatch = request.getRequestDispatcher("jsp/login.jsp");
			request.setAttribute("error", "Please login before accessing the system.");

			dispatch.forward(request, response);

			return;
		}

		Collection<Reservation> reservations;
		try {
			reservations = getExistingReservations(user);

			request.setAttribute("reservations", reservations);

		} catch (ValidationException e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		} catch (LoginException e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");

		} catch (Exception e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");
		}

		dispatch.forward(request, response);
	}

	/**
	 * Returns a collection of all the reservations that are in the system.
	 * 
	 * @param user
	 *            AirlineUser to use
	 * 
	 * @return Collection of all the reservations that are in the system.
	 * @throws ValidationException
	 *             Thrown if there is a problem in communicating with the remote
	 *             EJB.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws LoginException
	 *             Thrown if a problem occurs when logging the user in.
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private Collection<Reservation> getExistingReservations(AirlineUser user) throws ValidationException,
			LoginException, Exception {
		try {
			// TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)
			// ResourceUtil.getInitialContext().lookup(
			// Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");

			return ejbRef.getAllReservations();
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while retrieving all the reservations.");
			throw ve;
		}
	}
}
