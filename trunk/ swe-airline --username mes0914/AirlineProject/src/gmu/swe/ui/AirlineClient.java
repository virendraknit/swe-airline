package gmu.swe.ui;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.rmi.AirlineTicketReserver;
import gmu.swe.util.DateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * 
 * @author Matt Snyder
 * @course SWE 645
 * @date 09/17/2009
 */
public class AirlineClient {
	private String rmiUrl = "/AirlineTicketReserverServer";

	public AirlineClient(String blah) {
		/*
		 * Created so I can test individual methods.
		 */
	}

	/**
	 * Default Constructor - Creates the command line interface.
	 */
	public AirlineClient() {
		// Print out header
		System.out.println("");
		System.out.println("Travel Agent System");
		System.out.println("****************************************************");
		System.out.println("");
		System.out.println("");

		// Continue Flag
		boolean bContinue = true;

		// Continuous look until the user quits, or an error
		while (bContinue) {

			// Print out the command ment
			System.out.println("");
			System.out.println("** Options **");
			System.out.println("    1 - Search for Flights");
			System.out.println("    2 - Create Airplanes, Airports, and/or Flights");
			System.out.println("    3 - Quit");
			System.out.print("Please enter command (1-3)----> ");

			String userOption = this.readLine();
			char letterOption = '\0';

			// convert the input to a char
			if (userOption.length() > 1) {
				letterOption = '4';
			} else {
				letterOption = userOption.charAt(0);
			}

			// decide what to do, based on the input
			switch (letterOption) {
			case '1':
				// Search for flights
				searchFlights();
				break;
			case '2':
				populateDatabase();
				break;
			case '3':
				// Quit the program
				bContinue = false;
				System.out.println("Quiting - Good Bye");
				break;
			case '4':
			default:
				System.out.println("Error: Please enter only a number from the Options.");
				break;
			}
		}
	}

	private void populateDatabase() {
		boolean shouldContinue = true;

		while (shouldContinue) {

			// Display listing
			System.out.println("");
			System.out.println("***************************");
			System.out.println("** Adding Flight Data **");
			System.out.println("    1 - Create Airplane");
			System.out.println("    2 - Create Airport");
			System.out.println("    3 - Create Flight/Trip");
			System.out.println("    4 - Main Menu");
			System.out.println("    5 - Quit");
			System.out.print("Please enter command (1-5)----> ");

			shouldContinue = handleAddingDataChoice();

		}

	}

	private boolean handleAddingDataChoice() {
		while (true) {
			String userOption = this.readLine();

			char letterOption = '\0';

			// convert the input to a char
			if (userOption.length() != 1) {
				letterOption = 'q';
			} else {
				letterOption = userOption.charAt(0);
			}

			switch (letterOption) {
			case '1':
				createAirplane();
				return true;
			case '2':
				// create airport
				createAirport();
				return true;
			case '3':
				// create flight
				createFlight();
				return true;
			case '4':
				return false;
			case '5':
				System.out.println("Quiting - Good Bye");
				System.exit(1);
			default:
				System.out.print("Error: Please enter only a number from the available Options (1-5)----> ");
				break;
			}
		}
	}

	private void createFlight() {
		Flight flight = new Flight();
		System.out.println("");
		System.out.println("************************");
		System.out.println("** Create Flight/Trip **");
		System.out
				.println(" (Note: The seat count is automatically set based on the number of seats on the provided Airplane Id)");
		System.out.println("    R - Return (no updates will occur)");

		// Date departureDate = null;
		// String departureAirportCode = null;
		// String destinationAirportCode = null;
		// double cost = 0.0;
		// int airplaneId = 0;
		// int availableSeats = 0;
		boolean shouldReturn = false;

		while (true) {

			if (flight.getDepartureDate() == null) {
				shouldReturn = getDepartureDate(flight);
			} else if (flight.getDepartureAirportCode() == null) {
				shouldReturn = getDepartureAirportCode(flight);
			} else if (flight.getDestinationAirportCode() == null) {
				shouldReturn = getDestinationAirportCode(flight);
			} else if (flight.getCost() <= 0.0) {
				shouldReturn = getFlightCost(flight);
			} else if (flight.getAirplaneId() < 0) {
				shouldReturn = getAirplaneId(flight);
			} else {
				shouldReturn = createFlight(flight);
			}

			if (shouldReturn) {
				return;
			}
		}

	}

	private boolean createFlight(Flight flight) {
		try {
			AirlineTicketReserver reserver = (AirlineTicketReserver) Naming.lookup(rmiUrl);
			int flightId = reserver.createFlight(flight);
			System.out.println("");
			System.out.println("* Successfully added flight number " + flightId + ".");
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.print("Error: The URL provided for the Airline server is malformed.");
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.print("Error: Airline server is currently down, please try again later.");
			return true;
		} catch (NotBoundException e) {
			e.printStackTrace();
			System.out.print("Error: Airline server is currently down, please try again later.");
			return true;
		} catch (ValidationException e) {
			System.out.println("");
			System.out.println("****** Flight Creation Failed");
			System.out.println("Validation Error - Please see message(s) below:");
			showErrorMessages(e);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting to store data, please try again later.");
			return true;
		}
	}

	private boolean getAirplaneId(Flight flight) {
		System.out.print("Please enter the airplane Id that should be used for the flight, or R to return----> ");

		while (true) {
			String sAirplaneId = this.readLine();

			if (sAirplaneId == null || sAirplaneId.length() < 1) {
				System.out
						.print("Error: Please enter the airplane Id that should be used for the flight, or R to return----> ");
			} else if (sAirplaneId.equalsIgnoreCase("R")) {
				return true;
			} else {
				if (isWholeNumber(sAirplaneId)) {
					flight.setAirplaneId(Integer.parseInt(sAirplaneId));
					return false;
				} else {
					System.out
							.print(sAirplaneId
									+ " is an invalid Id. Please enter the airplane Id (a whole number) that should be used for the flight, or R to return----> ");
				}
			}

		}
	}

	private boolean getFlightCost(Flight flight) {
		System.out.print("Please enter the cost for the flight (ex: 150.00), or R to return----> ");

		while (true) {
			String cost = this.readLine();

			if (cost == null || cost.length() < 1) {
				System.out.print("Error: Please enter the cost for the flight (ex: 150.00), or R to return----> ");
			} else if (cost.equalsIgnoreCase("R")) {
				return true;
			} else {
				if (isValidCurrency(cost)) {
					flight.setCost(Double.parseDouble(cost));
					return false;
				} else {
					System.out
							.print("$"
									+ cost
									+ " is an invalid amount.  Please enter the cost for the flight (ex: 150.00), or R to return----> ");
				}
			}

		}
	}

	private boolean getDestinationAirportCode(Flight flight) {
		System.out.print("Please enter Destination Location Code for the flight, or R to return----> ");

		while (true) {
			String destinationLocation = this.readLine();

			if (destinationLocation == null || destinationLocation.length() < 1) {
				System.out.print("Error: Please enter Destination Location Code for the flight, or R to return----> ");
			} else if (destinationLocation.equalsIgnoreCase("R")) {
				return true;
			} else if (destinationLocation.equalsIgnoreCase(flight.getDepartureAirportCode())) {
				System.out
						.println("Error: The Destination Location Code may not be the same value as the Departing Location Code.");
				System.out.print("  Please enter Destination Location Code for the flight, or R to return----> ");
			} else {
				flight.setDestinationAirportCode(destinationLocation);
				return false;
			}

		}
	}

	private boolean getDepartureAirportCode(Flight flight) {
		System.out.print("Please enter Departure Location Code for the flight, or R to return----> ");

		while (true) {
			String departureLocation = this.readLine();

			if (departureLocation == null || departureLocation.length() < 1) {
				System.out.print("Error: Please enter Departure Location Code for the flight, or R to return----> ");
			} else if (departureLocation.equalsIgnoreCase("R")) {
				return true;
			} else {
				flight.setDepartureAirportCode(departureLocation);
				return false;
			}

		}
	}

	private boolean getDepartureDate(Flight flight) {
		System.out.print("Please enter the Date of the flight (MM/DD/YYYY), or R to return----> ");

		while (true) {

			String sDateOfFlight = this.readLine();

			if (sDateOfFlight == null || sDateOfFlight.length() < 1) {
				System.out.print("Error: Please enter the Date of the Trip (MM/DD/YYYY), or R to return----> ");
			} else if (sDateOfFlight.equalsIgnoreCase("R")) {
				return true;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date dateOfTrip = sdf.parse(sDateOfFlight);

					if (DateUtil.isTodayOrLater(dateOfTrip)) {
						flight.setDepartureDate(dateOfTrip);
						return false;
					} else {
						System.out
								.print("Error: Please enter the Date of the Trip that is today or later (MM/DD/YYYY), or R to return----> ");
					}
				} catch (ParseException e) {
					System.out
							.print("'"
									+ sDateOfFlight
									+ "' is an invalid date. Please enter a Date in the format MM/DD/YYYY, or R to return----> ");
				}
			}
		}
	}

	private void createAirport() {
		System.out.println("");
		System.out.println("*************************");
		System.out.println("** Create Airport Code **");
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter a new Airport Code, or R to return----> ");

		while (true) {
			String airportCode = this.readLine();

			if (airportCode == null || airportCode.length() < 1) {
				System.out.print("Error: Please enter a new Airport Code, or R to return----> ");
			} else if (airportCode.equalsIgnoreCase("R")) {
				return;
			} else {
				try {
					AirlineTicketReserver reserver = (AirlineTicketReserver) Naming.lookup(rmiUrl);
					reserver.createAirport(airportCode);
					System.out.println("");
					System.out.println("* Successfully added airport code " + airportCode + ".");
					return;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					System.out.print("Error: The URL provided for the Airline server is malformed.");
					return;
				} catch (RemoteException e) {
					e.printStackTrace();
					System.out.print("Error: Airline server is currently down, please try again later.");
					return;
				} catch (NotBoundException e) {
					e.printStackTrace();
					System.out.print("Error: Airline server is currently down, please try again later.");
					return;
				} catch (ValidationException e) {
					System.out.println("");
					System.out.println("****** Adding Airport Code Failed");
					System.out.println("Validation Error - Please see message(s) below:");
					showErrorMessages(e);
					System.out.print("Please enter a new Airport Code, or R to return----> ");
				} catch (DataAccessException e) {
					e.printStackTrace();
					System.out.print("Error while attempting to store data, please try again later.");
					return;
				}
			}

		}

	}

	private void createAirplane() {
		System.out.println("");
		System.out.println("*********************");
		System.out.println("** Create Airplane **");
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter the Airplane Type (DC-10, 747, etc.), or R to return----> ");

		String airplaneType = null;
		String sNumSeats = null;

		while (true) {
			String inValue = this.readLine();

			if (inValue == null || inValue.length() < 1) {
				if (airplaneType == null) {
					System.out.print("Error: Please enter the Airplane Type, or R to return----> ");
				} else {
					System.out.print("Error: Please enter the number of seats on the airplane, or R to return----> ");
				}
			} else if (inValue.equalsIgnoreCase("R")) {
				return;
			} else if (airplaneType == null) {
				airplaneType = inValue;
				System.out.print("Please enter the number of seats on the airplane, or R to return----> ");
			} else if (sNumSeats == null) {
				sNumSeats = inValue;
			}

			if (airplaneType != null && sNumSeats != null) {
				if (isWholeNumber(sNumSeats)) {
					Integer numSeats = new Integer(sNumSeats);
					if (numSeats < 1) {
						System.out
								.print("Error: Please enter a number value > 0 for the the number of seats on the airplane, or R to return----> ");
						sNumSeats = null;
					} else {

						try {
							AirlineTicketReserver reserver = (AirlineTicketReserver) Naming.lookup(rmiUrl);
							reserver.createAirplane(numSeats, airplaneType);
							System.out.println("");
							System.out.println("* Successfully added a " + airplaneType + " airplane with " + numSeats
									+ " seats.");
							return;
						} catch (MalformedURLException e) {
							e.printStackTrace();
							System.out.print("Error: The URL provided for the Airline server is malformed.");
							return;
						} catch (RemoteException e) {
							e.printStackTrace();
							System.out.print("Error: Airline server is currently down, please try again later.");
							return;
						} catch (NotBoundException e) {
							e.printStackTrace();
							System.out.print("Error: Airline server is currently down, please try again later.");
							return;
						} catch (ValidationException e) {
							System.out.println("");
							System.out.println("****** Airplane Creation Failed");
							System.out.println("Validation Error - Please see message(s) below:");
							showErrorMessages(e);
							return;
						} catch (DataAccessException e) {
							e.printStackTrace();
							System.out.print("Error while attempting to store data, please try again later.");
							return;
						}
					}
				} else {
					System.out
							.print("Error: Please enter a number value for the the number of seats on the airplane, or R to return----> ");
					sNumSeats = null;
				}
			}
		}

	}

	private void showErrorMessages(ValidationException e) {
		for (String errorMessage : e.getErrorMessages()) {
			System.out.println("-- " + errorMessage);
		}

	}

	private boolean isWholeNumber(String numSeats) {
		try {
			// Test if the value is actually a number
			new Integer(numSeats);
			return true;
		} catch (NumberFormatException e) {
			// Ignore
		}
		return false;
	}

	private boolean isValidCurrency(String currencyAmount) {
		try {
			// Test if the value is actually a number
			new Double(currencyAmount);
			return true;
		} catch (NumberFormatException e) {
			// Ignore
		}
		return false;
	}

	/**
	 * Method to list all of the registrations.
	 */
	private void searchFlights() {
		SearchFilters searchFilters = new SearchFilters();
		boolean shouldContinue = true;

		while (shouldContinue) {

			// Display listing
			System.out.println("");
			System.out.println("***************************");
			System.out.println("** Flight Search Options **");
			showCurrentSearchFilters(searchFilters);
			System.out.println("    1 - Set Departure Location");
			System.out.println("    2 - Set Destination");
			System.out.println("    3 - Set Date of Trip");
			System.out.println("    4 - Run Search (requires departure and destination, and/or date to be set)");
			System.out.println("    5 - Reset Search Filters");
			System.out.println("    6 - Main Menu");
			System.out.println("    7 - Quit");
			System.out.print("Please enter command (1-7)----> ");

			shouldContinue = handleFlightSearchChoice(searchFilters);

		}
	}

	/**
	 * Returns false if the user wants to quite, otherwise returns true.
	 * 
	 * @param searchFilters
	 * @return
	 */
	private boolean handleFlightSearchChoice(SearchFilters searchFilters) {
		while (true) {
			String userOption = this.readLine();

			char letterOption = '\0';

			// convert the input to a char
			if (userOption.length() != 1) {
				letterOption = 'q';
			} else {
				letterOption = userOption.charAt(0);
			}

			switch (letterOption) {
			case '1':
				setDepartureLocation(searchFilters);
				return true;
			case '2':
				setDestinationLocation(searchFilters);
				return true;
			case '3':
				setDateOfTrip(searchFilters);
				return true;
			case '4':
				search(searchFilters);
				return true;
			case '5':
				searchFilters.nullOutValues();
				return true;
			case '6':
				return false;
			case '7':
				System.out.println("Quiting - Good Bye");
				System.exit(1);
			default:
				System.out.print("Error: Please enter only a number from the available Options (1-7)----> ");
				break;
			}
		}
	}

	protected String showCurrentSearchFilters(SearchFilters searchFilters) {
		String filterDetails = "";

		if (!searchFilters.isAllNull()) {
			if (searchFilters.getDepartureLocation() != null) {
				filterDetails += "Departure (" + searchFilters.getDepartureLocation() + ")";
			}
			if (searchFilters.getDestinationLocation() != null) {
				if (filterDetails.length() > 0) {
					filterDetails += ", ";
				}
				filterDetails += "Destination (" + searchFilters.getDestinationLocation() + ")";
			}
			if (searchFilters.getDateOfTrip() != null) {
				if (filterDetails.length() > 0) {
					filterDetails += ", ";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				filterDetails += "Date of Trip (" + sdf.format(searchFilters.getDateOfTrip()) + ")";
			}
		} else {
			filterDetails = "NONE";
		}
		System.out.println("  Current Filters: " + filterDetails);

		return filterDetails;
	}

	private void setDepartureLocation(SearchFilters searchFilters) {
		System.out.println("");
		System.out.println("*********************************");
		System.out.println("** Set Departure Location Code **");
		showCurrentSearchFilters(searchFilters);
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter Departure Location Code, or R to return----> ");

		while (true) {
			String departureLocation = this.readLine();

			if (departureLocation == null || departureLocation.length() < 1) {
				System.out.print("Error: Please enter Departure Location Code, or R to return----> ");
			} else if (departureLocation.equalsIgnoreCase("R")) {
				return;
			} else {
				searchFilters.setDepartureLocation(departureLocation);
				return;
			}

		}

	}

	private void setDestinationLocation(SearchFilters searchFilters) {
		// Display listing
		System.out.println("");
		System.out.println("***********************************");
		System.out.println("** Set Destination Location Code **");
		showCurrentSearchFilters(searchFilters);
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter Destination Location Code, or R to return----> ");

		while (true) {

			String destinationLocation = this.readLine();

			if (destinationLocation == null || destinationLocation.length() < 1) {
				System.out.print("Error: Please enter Destination Location Code, or R to return----> ");
			} else if (destinationLocation.equalsIgnoreCase("R")) {
				return;
			} else {
				searchFilters.setDestinationLocation(destinationLocation);
				return;
			}

		}

	}

	private void setDateOfTrip(SearchFilters searchFilters) {
		// Display listing
		System.out.println("");
		System.out.println("****************************");
		System.out.println("** Set Date of Trip **");
		showCurrentSearchFilters(searchFilters);
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter the Date of the Trip (MM/DD/YYYY), or R to return----> ");

		while (true) {

			String sDateOfTrip = this.readLine();

			if (sDateOfTrip == null || sDateOfTrip.length() < 1) {
				System.out.print("Error: Please enter the Date of the Trip (MM/DD/YYYY), or R to return----> ");
			} else if (sDateOfTrip.equalsIgnoreCase("R")) {
				return;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date dateOfTrip = sdf.parse(sDateOfTrip);
					searchFilters.setDateOfTrip(dateOfTrip);
					return;
				} catch (ParseException e) {
					System.out
							.print("'"
									+ sDateOfTrip
									+ "' is an invalid date. Please enter a Date in the format MM/DD/YYYY, or R to return----> ");
				}
			}

		}

	}

	private void search(SearchFilters searchFilters) {
		if (searchFilters.isAllNull()) {
			System.out.println("");
			System.out
					.println("* Error: Running a search requires the Departure and Destination, and/or Date to be set.");
		} else if (searchFilters.getDepartureLocation() != null || searchFilters.getDestinationLocation() != null
				|| searchFilters.getDateOfTrip() != null) {
			Collection<Flight> flights = runSearch(searchFilters);
			if (flights != null) {
				reserveProcess(flights);
			} else {
				System.out.println("");
				System.out.println("* No flights were found with the provided search options.");
			}
		} else {
			System.out.println("");
			System.out.println("* Error: Running a search requires the Departure, Destination, and/or Date to be set.");
		}
	}

	private Collection<Flight> runSearch(SearchFilters searchFilters) {
		try {
			AirlineTicketReserver reserver = (AirlineTicketReserver) Naming.lookup(rmiUrl);
			return reserver.search(searchFilters);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.print("Error: The URL provided for the Airline server is malformed.");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.print("Error: Airline server is currently down, please try again later.");
		} catch (NotBoundException e) {
			e.printStackTrace();
			System.out.print("Error: Airline server is currently down, please try again later.");
		} catch (ValidationException e) {
			System.out.println("");
			System.out.println("****** Flight Search Failed");
			System.out.println("Validation Error - Please see message(s) below:");
			showErrorMessages(e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting to access data, please try again later.");
		}

		return null;
	}

	private void reserveProcess(Collection<Flight> flights) {
		showFlights(flights);

		System.out.println("");
		System.out.println("************************");
		System.out.println("** Reserving a Flight **");
		System.out.println("    R - Return (no updates will occur)");

		// Date departureDate = null;
		// String departureAirportCode = null;
		// String destinationAirportCode = null;
		// double cost = 0.0;
		// int airplaneId = 0;
		// int availableSeats = 0;
		StringBuffer sFlightIdBuf = new StringBuffer();
		StringBuffer sNumOfSeatsBuf = new StringBuffer();
		
		boolean shouldReturn = false;

		while (true) {
			if(sFlightIdBuf.length() < 1){
				shouldReturn = getFlightId(sFlightIdBuf);
			}else if(sNumOfSeatsBuf.length() < 1){
				shouldReturn = getNumberOfSeatsToReserve(sNumOfSeatsBuf);
			}else{
				shouldReturn = createReservation(Integer.parseInt(sFlightIdBuf.toString()), Integer.parseInt(sNumOfSeatsBuf.toString()));
			}

			if (shouldReturn) {
				return;
			}
		}

	}

	private boolean createReservation(int flightId, int numSeats) {
		try {
			AirlineTicketReserver reserver = (AirlineTicketReserver) Naming.lookup(rmiUrl);
			Reservation reservation = reserver.createReservation(flightId, numSeats);
			
			
			System.out.println("");
			System.out.println("* Successfully added flight number " + flightId + ".");
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.print("Error: The URL provided for the Airline server is malformed.");
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.print("Error: Airline server is currently down, please try again later.");
			return true;
		} catch (NotBoundException e) {
			e.printStackTrace();
			System.out.print("Error: Airline server is currently down, please try again later.");
			return true;
		} catch (ValidationException e) {
			System.out.println("");
			System.out.println("****** Flight Creation Failed");
			System.out.println("Validation Error - Please see message(s) below:");
			showErrorMessages(e);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting to store data, please try again later.");
			return true;
		}
	}

	private boolean getNumberOfSeatsToReserve(StringBuffer numOfSeatsBuf) {
		System.out.print("Please enter the number of seats to reserve, or R to return----> ");

		while (true) {
			String sNumSeats = this.readLine();

			if (sNumSeats == null || sNumSeats.length() < 1) {
				System.out.print("Error: Please enter the number of seats to reserve, or R to return----> ");
			} else if (sNumSeats.equalsIgnoreCase("R")) {
				return true;
			} else {
				if (isWholeNumber(sNumSeats)) {
					if (Integer.parseInt(sNumSeats) < 0) {
						System.out.print("Error: Please enter the number of seats to reserve (the Id should be >= 1), or R to return----> ");
					} else {
						numOfSeatsBuf.append(sNumSeats);
						return false;
					}
				} else {
					System.out
							.print(sNumSeats
									+ " is an invalid number. Please enter the number of seats (a whole number) to reserve, or R to return----> ");
				}
			}

		}
	}

	private boolean getFlightId(StringBuffer sFlightIdBuf) {
		System.out.print("Please enter the flight Id for the reservation, or R to return----> ");

		while (true) {
			String sFlightId = this.readLine();

			if (sFlightId == null || sFlightId.length() < 1) {
				System.out.print("Error: Please enter the flight Id for the reservation, or R to return----> ");
			} else if (sFlightId.equalsIgnoreCase("R")) {
				return true;
			} else {
				if (isWholeNumber(sFlightId)) {
					if (Integer.parseInt(sFlightId) < 0) {
						System.out.print("Error: Please enter the flight Id for the reservation (the Id should be >= 0), or R to return----> ");
					} else {
						sFlightIdBuf.append(sFlightId);
						return false;
					}
				} else {
					System.out
							.print(sFlightId
									+ " is an invalid Id. Please enter the flight Id (a whole number) for the reservation, or R to return----> ");
				}
			}

		}
	}

	private void showFlights(Collection<Flight> flights) {
		System.out.println("*****************");
		System.out.println("** Flight List **");
		System.out.println("(Ordered by Departing Airport and then by Destination Airport)");
		System.out.println("");
		System.out.println("DEPARTING\tDESTINATION\t\t\t\t\t\tAVAILABLE");
		System.out.println("AIRLINE\t\tAIRLINE\t\tDEPART DATE\tFLIGHT ID\tCOST\tSEATS");
		System.out.println("--------------------------------------------------------------------------------");

		for (Flight flight : flights) {
			String airplane = flight.getDepartureAirportCode() + "\t\t" + flight.getDestinationAirportCode() + "\t\t"
					+ flight.getDepartureDate() + "\t" + flight.getId() + "\t\t$" + flight.getCost() + "\t"
					+ flight.getAvailableSeats();
			System.out.println(airplane);
		}
		System.out.println("--------------------------------------------------------------------------------");

	}

	private String readLine() {
		BufferedReader bRead = new BufferedReader(new InputStreamReader(System.in));
		String inLine = "";
		try {
			inLine = bRead.readLine();
		} catch (Exception e) {
			System.out.println("Error reading input: " + e.toString());
		}

		return inLine.trim();
	}

	/**
	 * Main method to run from command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// String host = "";
		// String port = "";
		// String registryName = "";
		// if (args.length != 3) {
		// System.out
		// .println("Usage: java gmartinc.swe645.examples.rmi.emailreg.client.EmailRegClient [host] [port] [Registry Name]");
		// return;
		// } else {
		// // get the RMI connection info
		// host = args[0];
		// port = args[1];
		// registryName = args[2];
		// }

		new AirlineClient();
	}
}