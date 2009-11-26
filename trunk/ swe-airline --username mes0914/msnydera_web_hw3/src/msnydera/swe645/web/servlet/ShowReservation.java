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
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet used to get a reservation and forward it to the reservation view JSP.
 */
public class ShowReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowReservation() {
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
	 *      response) Retrieves the provided Reservation Id and sends it to the
	 *      reservation view JSP.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/reservation.jsp");

		Reservation reservation = null;
		try {
			reservation = getReservation(Integer.parseInt(request.getParameter("reservationId")));

			request.setAttribute("reservation", reservation);

		} catch (ValidationException e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		} catch (Exception e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "An unkown error occured.");
		}

		dispatch.forward(request, response);
	}

	/**
	 * Returns the Reservation associated with the provided reservationId.
	 * 
	 * @return The reservation.
	 * @throws ValidationException
	 *             Thrown if there is a problem in communicating with the remote
	 *             EJB.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private Reservation getReservation(int reservationId) throws ValidationException, Exception {
		try {
			 TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)
			 ResourceUtil.getInitialContext().lookup(Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
//			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
//					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");

			return ejbRef.getReservation(reservationId);
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while retrieving reservation.");
			throw ve;
		}
	}
}
