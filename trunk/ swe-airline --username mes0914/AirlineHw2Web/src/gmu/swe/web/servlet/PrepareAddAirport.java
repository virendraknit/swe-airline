package gmu.swe.web.servlet;

import gmu.swe.domain.Airplane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
		
		Collection<String> airports = getExistingAirports();
		request.setAttribute("airportCodes", airports);
		
		request.setAttribute("addedAirport", request.getAttribute("addedAirport"));
		request.setAttribute("error", request.getAttribute("error"));
		
		dispatch.forward(request, response);
	}

	private Collection<String> getExistingAirports() {
		Collection<String> airports = new ArrayList<String>();
		
		airports.add("BWI");
		airports.add("IAD");
		airports.add("WAS");
		
		return airports;
	}

}
