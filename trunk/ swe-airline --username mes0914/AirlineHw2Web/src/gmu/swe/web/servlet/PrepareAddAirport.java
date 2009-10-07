package gmu.swe.web.servlet;

import gmu.swe.constant.Constants;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.ejb.HeadquartersEjbRemote;
import gmu.swe.util.ResourceUtil;
import gmu.swe.util.StringUtils;

import java.io.IOException;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrepareAddAirport
 */
public class PrepareAddAirport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrepareAddAirport() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/addAirport.jsp");
		
		Collection<String> airports;
		try {
			airports = getExistingAirports();
			
			request.setAttribute("airportCodes", airports);
			
			request.setAttribute("addedAirport", request.getAttribute("addedAirport"));
			request.setAttribute("error", request.getAttribute("error"));
			
		} catch (ValidationException e) {
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		}
		
		dispatch.forward(request, response);
	}

	public Collection<String> getExistingAirports() throws ValidationException {
		try {
			HeadquartersEjbRemote ejbRef = (HeadquartersEjbRemote) ResourceUtil.getInitialContext().lookup(
					Constants.EAR_FILE_NAME + "/HeadquartersEjb/remote");
			return ejbRef.getAllAirports();
		} catch (NamingException e) {
			e.printStackTrace();
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while retrieving all the airports.");
			throw ve;
		}
	}
	
//	private Collection<String> getExistingAirportsTest() {
//		Collection<String> airports = new ArrayList<String>();
//		
//		airports.add("BWI");
//		airports.add("IAD");
//		airports.add("WAS");
//		
//		return airports;
//	}

}
