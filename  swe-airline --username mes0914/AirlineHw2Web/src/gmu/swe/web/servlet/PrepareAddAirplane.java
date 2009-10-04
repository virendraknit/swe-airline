package gmu.swe.web.servlet;

import gmu.swe.domain.Airplane;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.ejb.HeadquartersEjb;
import gmu.swe.service.ejb.HeadquartersEjbRemote;
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
 * Servlet implementation class PrepareAddAirplane
 */
public class PrepareAddAirplane extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrepareAddAirplane() {
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
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/addAirplane.jsp");

		Collection<Airplane> airplanes;
		try {
			airplanes = getExistingAirplanes();

			request.setAttribute("airplanes", airplanes);

			request.setAttribute("addedAirplane", request.getAttribute("addedAirplane"));
			request.setAttribute("error", request.getAttribute("error"));

		} catch (ValidationException e) {
			String errorMessage = StringUtils.getFormattedMessages(e.getErrorMessages());
			request.setAttribute("error", errorMessage);
		}

		dispatch.forward(request, response);
	}

	public Collection<Airplane> getExistingAirplanes() throws ValidationException {

		try {
			HeadquartersEjbRemote ejbRef = (HeadquartersEjbRemote) ResourceUtil.getInitialContext().lookup(
					"HeadquartersEjb/remote");
			return ejbRef.getAllAirplanes();
		} catch (NamingException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured during EJB lookup.");
			throw ve;
		} catch (DataAccessException e) {
			ValidationException ve = new ValidationException();
			ve.addErrorMessage("Server error occured while retrieving all the airplanes.");
			throw ve;
		}
	}

	// private Collection<Airplane> getExistingAirplanesTest() {
	// Collection<Airplane> airplanes = new ArrayList<Airplane>();
	//		
	// Airplane airplane = new Airplane();
	// airplane.setType("DC-10");
	// airplane.setNumSeats(150);
	// airplanes.add(airplane);
	//		
	// airplane = new Airplane();
	// airplane.setType("737");
	// airplane.setNumSeats(180);
	// airplanes.add(airplane);
	//		
	// airplane = new Airplane();
	// airplane.setType("747");
	// airplane.setNumSeats(200);
	// airplanes.add(airplane);
	//		
	// airplane = new Airplane();
	// airplane.setType("F-14");
	// airplane.setNumSeats(2);
	// airplanes.add(airplane);
	//		
	//		
	// return airplanes;
	// }

}
