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
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet that prepares the Request object for the search page.
 */
public class PrepareSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrepareSearch() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) Adds all of the airports in the system to the Request
	 *      object so the user can see the exiting airports.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/search.jsp");

		AirlineUser user = ResourceUtil.getLoggedInUser(request.getSession());

		if (user == null) {
			dispatch = request.getRequestDispatcher("jsp/login.jsp");
			request.setAttribute("error", "Please login before accessing the system.");

			dispatch.forward(request, response);

			return;
		}

		Collection<String> airports = null;
		try {
			airports = getAllAirports(user);
			request.setAttribute("airports", airports);
			request.setAttribute("error", request.getAttribute("error"));

		} catch (ValidationException e) {
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		} catch (LoginException e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");

		} catch (Exception e) {
			dispatch = request.getRequestDispatcher("jsp/home.jsp");

			request.setAttribute("error", "Your role does not allow you to perform this action.");
		}

		request.setAttribute("airports", airports);
		request.setAttribute("error", request.getAttribute("error"));
		dispatch.forward(request, response);
	}

	/**
	 * Returns all the airports in the system.
	 * 
	 * @param user
	 *            AirlineUser to use
	 * 
	 * @return Collection of all the airport codes in the system.
	 * @throws ValidationException
	 *             Thrown if a problem occurs in communicating with the remote
	 *             EJB.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws LoginException
	 *             Thrown if a problem occurs when logging the user in.
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private Collection<String> getAllAirports(AirlineUser user) throws ValidationException, LoginException, Exception {
		try {
			// TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)
			// ResourceUtil.getInitialContext().lookup(
			// Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");

			return ejbRef.getAllAirports();
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while looking up existing airports.");
			throw ve;
		}
	}
}
