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
 * Servlet implementation class AddAirplane
 */
public class AddAirplane extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAirplane() {
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
		RequestDispatcher dispatch = request.getRequestDispatcher("/prepareAddAirplane");
//		Airplane Type: <input type="text" name="airplaneType"> <br /> 
//	 	Number of Seats: <input type="text" name="airplaneType"> <br />
//		
		Airplane airplane = getAirplane(request);
		String errorMessage = validateAirplane(airplane);
		
		if(errorMessage == null){
			addAirplane(airplane);
			request.setAttribute("addedAirplane", airplane);
		}else{
			request.setAttribute("error", errorMessage);
		}
		
		dispatch.forward(request, response);
	}

	private void addAirplane(Airplane airplane) {
		// TODO Auto-generated method stub
		
	}

	private String validateAirplane(Airplane airplane) {
		String errorMessage = null;
		
		if (airplane.getNumSeats() < 1) {
			errorMessage = "The number of seats on a plane may not be < 1";
		}else if (airplane.getType() == null || airplane.getType().trim().equals("")) {
			errorMessage = "The airplane type was not provided";
		}
		
		return errorMessage;
	}

	private Airplane getAirplane(HttpServletRequest request) {
		String type = request.getParameter("airplaneType");
		String sNumSeats = request.getParameter("numSeats");
		Airplane airplane = new Airplane();
		
		airplane.setType((type == null ? null : type.trim()));
		airplane.setNumSeats((NumberUtils.isWholeNumber(sNumSeats) ? Integer.parseInt(sNumSeats) : -1));
		
		return airplane;
	}

}
