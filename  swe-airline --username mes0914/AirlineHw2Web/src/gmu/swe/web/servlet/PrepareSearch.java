package gmu.swe.web.servlet;

import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.ejb.TravelAgentEjbRemote;
import gmu.swe.util.ResourceUtil;
import gmu.swe.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrepareSearch
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
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/search.jsp");

		Collection<String> airports = null;
		try {
			airports = getAllAirports();
			request.setAttribute("airports", airports);
			request.setAttribute("error", request.getAttribute("error"));

		} catch (ValidationException e) {
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		}

		request.setAttribute("airports", airports);
		request.setAttribute("error", request.getAttribute("error"));
		dispatch.forward(request, response);
	}

	private Collection<String> getAllAirports() throws ValidationException {
		try {
			TravelAgentEjbRemote ejbRef = (TravelAgentEjbRemote) ResourceUtil.getInitialContext().lookup(
					"TravelAgentEjb/remote");

			return ejbRef.getAllAirports();
		} catch (NamingException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while looking up existing airports.");
			throw ve;
		}
	}

	private ArrayList<String> getAllAirportsTest() {

		ArrayList<String> airports = new ArrayList<String>();
		airports.add("BWI");
		airports.add("IAD");
		airports.add("WAS");
		airports.add("RAG");

		return airports;
	}

}
