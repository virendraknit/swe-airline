package gmu.swe.web.servlet;

import gmu.swe.domain.Flight;
import gmu.swe.domain.SearchFilters;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReserveFlight
 */
public class ReserveFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveFlight() {
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
		RequestDispatcher dispatch = request.getRequestDispatcher("jsp/searchResults.jsp");

		String flightId = request.getParameter("flightId");
		String numSeats = request.getParameter("numSeats");
		
		if(!isValidSeatNumber(numSeats) || !flightHasEnoughSeats(numSeats)){
			dispatch = request.getRequestDispatcher("jsp/searchResults.jsp");
			String errorMessage = "Please enter a seat number > 0 and <= the number of available seats on the flight..";
			request.setAttribute("error", errorMessage);
		}
		
		dispatch.forward(request, response);
	}

	private boolean flightHasEnoughSeats(String numSeats) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isValidSeatNumber(String numSeats) {
		if(isWholeNumber(numSeats)){
			return Integer.parseInt(numSeats) > 0;
		}
		return false;
	}

	/**
	 * Utility method that determines if the provided string is a whole number.
	 * Returns true if it is, false if it isn't (this could be because it is
	 * text or a floating number.)
	 * 
	 * @param sNumber
	 *            The String to check
	 * @return True of sNumber is a whole number, otherwise returns false.
	 */
	private boolean isWholeNumber(String sNumber) {
		try {
			// Test if the value is actually a number
			new Integer(sNumber);
			return true;
		} catch (NumberFormatException e) {
			// Ignore, this means it isn't a whole number.
		}
		return false;
	}
}
