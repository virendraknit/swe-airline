package gmu.swe.web.servlet;

import java.io.IOException;
import java.util.ArrayList;

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

		ArrayList<String> airports = getAllAirports();

		request.setAttribute("airports", airports);
		request.setAttribute("error", request.getAttribute("error"));
		dispatch.forward(request, response);
	}

	private ArrayList<String> getAllAirports() {
		ArrayList<String> airports = new ArrayList<String>();
		airports.add("BWI");
		airports.add("IAD");
		airports.add("WAS");
		airports.add("RAG");

		return airports;
	}

}