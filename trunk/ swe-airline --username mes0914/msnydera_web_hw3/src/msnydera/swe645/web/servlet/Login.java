/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.web.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import msnydera.swe645.constant.Constants;
import msnydera.swe645.domain.AirlineUser;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.HeadquartersEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet used to prepare the request object for the page to add airports.
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
	 *      response) Logs in the user.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/login.jsp");

		AirlineUser user = new AirlineUser();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));

		try {
			validateUserCredentials(user);

			HttpSession session = request.getSession();
			session.setAttribute(Constants.CURRENT_USER, user);

			dispatch = request.getRequestDispatcher("jsp/home.jsp");

		} catch (ValidationException e) {
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);

		}

		dispatch.forward(request, response);
	}

	/**
	 * Validates that the provided user object that the username and password
	 * pair exists in the system. If either are null or empty strings, and
	 * ValidtionException will be thrown. If the username and password don't
	 * match, a ValidtionException will be thrown.
	 * 
	 * @param user
	 *            AirlineUser that is logged in
	 * @throws ValidationException
	 *             Thrown if username or password are null or empty, or if the
	 *             user doesn't exist or if the password isn't correct for the
	 *             given user.
	 */
	private void validateUserCredentials(AirlineUser user) throws ValidationException {
		ValidationException error = new ValidationException();

		if (user == null) {
			error.addErrorMessage("A username and password must be provided.");
		} else {
			if (user.getUsername() == null || user.getUsername().trim().length() < 1) {
				error.addErrorMessage("A username must be provided.");
			}
			if (user.getPassword() == null || user.getPassword().trim().length() < 1) {
				error.addErrorMessage("A password must be provided.");
			}
		}

		if (error.hasErrors()) {
			throw error;
		}

	}
}
