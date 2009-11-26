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
import msnydera.swe645.domain.Customer;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.ejb.TravelAgentEjbRemote;
import msnydera.swe645.util.ResourceUtil;
import msnydera.swe645.util.StringUtils;

/**
 * Servlet used to add an airplane to the system.
 */
public class CreateCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateCustomer() {
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
	 *      response) Handles the user's input and forwards him back to the
	 *      PrepareAddAirplane servlet
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("/prepareCreateCustomer");

		Customer customer = getCustomer(request);
		String errorMessage = validateCustomer(customer);

		if (errorMessage == null) {
			try {
				createCustomer(customer);

				request.setAttribute("createdCustomer", customer);
			} catch (ValidationException e) {
				errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
				request.setAttribute("error", errorMessage);
			} catch (Exception e) {
				dispatch = request.getRequestDispatcher("jsp/home.jsp");

				request.setAttribute("error", "Your role does not allow you to perform this action.");
			}

		} else {
			request.setAttribute("error", errorMessage);
		}

		dispatch.forward(request, response);
	}

	/**
	 * Connects to the TravelAgent EJB to create a new customer in the system.
	 * 
	 * @param customer
	 *            Customer to add
	 * @throws ValidationException
	 *             Thrown if a validation error occurred.
	 * @throws ValidationException
	 *             Thrown to help with messages
	 * @throws Exception
	 *             Thrown if an error occurs with the connection to the DB with
	 *             the user.
	 */
	private void createCustomer(Customer customer) throws ValidationException, Exception {
		try {
			 TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote)
			 ResourceUtil.getInitialContext().lookup(Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
//			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getLoggedInContext(user).lookup(
//					Constants.EAR_FILE_NAME + "/TravelAgentEjb/remote");
			ejbRef.createCustomer(customer);
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
	 * Performs validation on the Customer object to determine if the fields
	 * contain appropriate data. This method returns null if there are no
	 * problems with the data, otherwise this method will return an error
	 * message describing what is wrong.
	 * 
	 * @param customer
	 *            Customer to validate
	 * @return Null if the customer contains valid data, otherwise returns an
	 *         error message.
	 */
	private String validateCustomer(Customer customer) {
		String errorMessage = "";

		if (customer.getName() == null || customer.getName().length() < 1) {
			errorMessage += "A name must be provided";
		}
		if (customer.getAddress() == null || customer.getAddress().length() < 1) {
			if (errorMessage.length() > 0) {
				errorMessage += "<br />";
			}
			errorMessage += "An address must be provided";
		}
		if (customer.getPhone() == null || customer.getPhone().length() < 1) {
			if (errorMessage.length() > 0) {
				errorMessage += "<br />";
			}

			errorMessage += "A phone number must be provided";
		}

		return (errorMessage.length() < 1 ? null : errorMessage);
	}

	/**
	 * Binds the information in the request to a Customer object and returns
	 * that object.
	 * 
	 * @param request
	 *            Request containing the information to bind.
	 * @return Customer object with the bound information.
	 */
	private Customer getCustomer(HttpServletRequest request) {
		String name = request.getParameter("customerName");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");

		Customer customer = new Customer();
		customer.setName((name == null ? null : name.trim()));
		customer.setAddress((address == null ? null : address.trim()));
		customer.setPhone((phone == null ? null : phone.trim()));

		return customer;
	}
}
