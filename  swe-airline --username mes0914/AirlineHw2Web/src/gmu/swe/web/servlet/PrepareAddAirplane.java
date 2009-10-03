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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/addAirplane.jsp");
		
		Collection<Airplane> airplanes = getExistingAirplanes();
		request.setAttribute("airplanes", airplanes);
		
		System.out.println("Added Airplane: " + request.getAttribute("addedAirplane"));
		System.out.println("Errors: " + request.getAttribute("error"));
		
		request.setAttribute("addedAirplane", request.getAttribute("addedAirplane"));
		request.setAttribute("error", request.getAttribute("error"));
		
		dispatch.forward(request, response);
	}

	private Collection<Airplane> getExistingAirplanes() {
		Collection<Airplane> airplanes = new ArrayList<Airplane>();
		
		Airplane airplane = new Airplane();
		airplane.setType("DC-10");
		airplane.setNumSeats(150);
		airplanes.add(airplane);
		
		airplane = new Airplane();
		airplane.setType("737");
		airplane.setNumSeats(180);
		airplanes.add(airplane);
		
		airplane = new Airplane();
		airplane.setType("747");
		airplane.setNumSeats(200);
		airplanes.add(airplane);
		
		airplane = new Airplane();
		airplane.setType("F-14");
		airplane.setNumSeats(2);
		airplanes.add(airplane);
		
		
		return airplanes;
	}

}
