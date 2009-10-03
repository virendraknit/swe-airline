package gmu.swe.web.servlet;

import gmu.swe.domain.Airplane;
import gmu.swe.util.NumberUtils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddAirport
 */
public class AddAirport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAirport() {
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
		RequestDispatcher dispatch = request.getRequestDispatcher("/prepareAddAirport");
		
		String airportCode = request.getParameter("airportCode");
		String errorMessage = validateAirport(airportCode);
		
		if(errorMessage == null){
			addAirport(airportCode);
			request.setAttribute("addedAirportCode", airportCode);
		}else{
			request.setAttribute("error", errorMessage);
		}
		
		dispatch.forward(request, response);
	}

	private void addAirport(String airport) {
		// TODO Auto-generated method stub
		
	}

	private String validateAirport(String airportCode) {
		String errorMessage = null;
		
		if (airportCode == null || airportCode.trim().equals("")) {
			errorMessage = "The airport code was not provided";
		}
		
		return errorMessage;
	}
}
