package msnydera.swe645.web.servlet;

import java.io.IOException;

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
import msnydera.swe645.util.NumberUtils;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet implementation class CancelReservation
 */
public class CancelReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancelReservation() {
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
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/reservation.jsp");

		AirlineUser user = ResourceUtil.getLoggedInUser(request.getSession());

		if (user == null) {
			dispatch = request.getRequestDispatcher("jsp/login.jsp");
			request.setAttribute("error", "Please login before accessing the system.");

			dispatch.forward(request, response);

			return;
		}

		String reservationId = request.getParameter("reservationId");
		String errorMessage = validateReservationId(reservationId);

		if (errorMessage == null) {
			try {
				Reservation reservation = cancelReservation(reservationId, user);
				request.setAttribute("reservation", reservation);

			} catch (ValidationException e) {
				dispatch = request.getRequestDispatcher("jsp/home.jsp");
				errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
				request.setAttribute("error", errorMessage);
			} catch (LoginException e) {
				dispatch = request.getRequestDispatcher("jsp/home.jsp");

				request.setAttribute("error", "Your role does not allow you to perform this action.");

			} catch (Exception e) {
				dispatch = request.getRequestDispatcher("jsp/home.jsp");

				request.setAttribute("error", "Your role does not allow you to perform this action.");
			}

		} else {
			request.setAttribute("error", errorMessage);
			dispatch = request.getRequestDispatcher("jsp/home.jsp");
		}

		dispatch.forward(request, response);
	}

	/**
	 * Communicates with the remote EJB service to cancel a reservation.
	 * 
	 * @param reservationId
	 *            Reservation Id to cancel
	 * @param user
	 *            AirlineUser to use
	 * @throws ValidationException
	 *             Thrown if there was an error with the validation of the
	 *             provided reservation Id, or if there was an issue in
	 *             communicating with the remote service.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws LoginException
	 *             Thrown if a problem occurs when logging the user in.
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private Reservation cancelReservation(String reservationId, AirlineUser user) throws ValidationException,
			LoginException, Exception {
		try {
			// TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)
			// ResourceUtil.getInitialContext().lookup(
			// Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			Reservation reservation = ejbRef.cancelReservation(Integer.parseInt(reservationId));

			return reservation;

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

	private String validateReservationId(String reservationId) {
		String errorMessage = null;

		if (null == reservationId) {
			errorMessage = "The reservation Id was not provided";
		} else if (!NumberUtils.isWholeNumber(reservationId) || Integer.parseInt(reservationId) < 0) {
			errorMessage = "The reservation Id must be >= 0.";
		}

		return errorMessage;
	}

}
