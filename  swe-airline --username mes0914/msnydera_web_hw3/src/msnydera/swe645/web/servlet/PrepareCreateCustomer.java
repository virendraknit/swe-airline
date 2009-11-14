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
import msnydera.swe645.domain.Customer;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet handling the preparation for the AddAirplane page.
 */
public class PrepareCreateCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrepareCreateCustomer() {
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
	 *      response) This method will prepare the request with the existing
	 *      airplanes in the system (so the user can see what is currently
	 *      available).
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/createCustomer.jsp");
		
		AirlineUser user = ResourceUtil.getLoggedInUser(request.getSession());
		
		if(user == null){
			dispatch = request.getRequestDispatcher("jsp/login.jsp");
			request.setAttribute("error", "Please login before accessing the system.");
			
			dispatch.forward(request, response);
			
			return;
		}
		
		try {
			Collection<Customer> customers = getCustomers(user);

			request.setAttribute("customers", customers);
			request.setAttribute("createdCustomer", request.getAttribute("createdCustomer"));
			
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

		dispatch.forward(request, response);
	}

	/**
	 * Communicates with the remote EJB service to retrieve all of the
	 * customers.
	 * @param user The user to use.
	 * 
	 * @return Collection of customers.
	 */
	private Collection<Customer> getCustomers(AirlineUser user) throws ValidationException, LoginException, Exception {
		try {
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			
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
}
