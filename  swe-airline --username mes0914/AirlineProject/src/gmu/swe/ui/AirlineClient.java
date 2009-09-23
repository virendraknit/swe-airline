package gmu.swe.ui;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.rmi.AirlineHeadquartersRemoteService;
import gmu.swe.util.DateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * This is the user interface for the airline system. This class may be used to
 * add items to the database, and search and reserve flights. In other words,
 * this is the Travel Agent and Airline Ticket Reservation Headquarters.
 * 
 * Some of the code here was copied from the in class example provided by
 * Professor Martin (which is why he is listed as an author as well)
 * 
 * @author Matt Snyder
 * @author Greg Martin
 * @course SWE 645
 * @date 09/17/2009
 */
public class AirlineClient {
	private String rmiUrl = "/AirlineHeadquartersRemoteServer";

	/**
	 * This constructor is never called. It is only here so this class can be
	 * tested with JUnit.
	 * 
	 * @param notUsed
	 */
	public AirlineClient(String notUsed) {
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
		System.out.println("Travel Agent & Airline System");
		System.out.println("****************************************************");

		// Continue Flag
		boolean bContinue = true;

		// Continuous look until the user quits, or an error
		while (bContinue) {

			// Print out the command ment
			System.out.println("");
			System.out.println("***************");
			System.out.println("** Main Menu **");
			System.out.println("    1 - Search for Flights");
			System.out.println("    2 - Create Airplanes, Airports, and/or Flights");
			System.out.println("    3 - Quit");
			System.out.print("Please enter command (1-3)----> ");

			bContinue = handleMainMenuChoice();
		}
	}

	/**
	 * Handles the user input from the main menu (including invalid inputs)
	 * 
	 * @return True if the program should continue to run, false if it should
	 *         shutdown.
	 */
	private boolean handleMainMenuChoice() {

		while (true) {
			String userOption = this.readLine();
			char letterOption = '\0';

			// convert the input to a char
			if (userOption.length() != 1) {
				letterOption = '4';
			} else {
				letterOption = userOption.charAt(0);
			}

			// decide what to do, based on the input
			switch (letterOption) {
			case '1':
				// Search for flights
				searchFlights();
				return true;
			case '2':
				populateDatabase();
				return true;
			case '3':
				// Quit the program
				System.out.println("Quiting - Good Bye");
				return false;
			case '4':
			default:
				System.out.print("Error: Please enter only a number from the Options (1-3)----> ");
				break;
			}
		}
	}

	/**
	 * This menu handles the adding of airplanes, airports, and flights to the
	 * system.
	 */
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

	/**
	 * This method handles the user input from the "Adding Flight Data" menu.
	 * 
	 * @return True if the "Adding Flight Data" menu should be displayed again.
	 *         False if the Main Menu should be displayed. If the user chooses
	 *         the "Quit" option, the program can also end here.
	 */
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

	/**
	 * This method displays the menu for creating flights. It also displays all
	 * of the airplanes and airports in the system.
	 */
	private void createFlight() {
		Flight flight = new Flight();
		System.out.println("");
		System.out.println("************************");
		System.out.println("** Create Flight/Trip **");
		System.out
				.println(" (Note: The seat count is automatically set based on the number of seats on the provided Airplane Id)");
		System.out.println("    R - Return (no updates will occur)");

		boolean shouldReturn = false;

		Collection<Airplane> airplanes = new ArrayList<Airplane>();
		Collection<String> airports = new ArrayList<String>();

		System.out.println("");
		System.out.println("Available Airplanes & Airports");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------");
		shouldReturn = showAvailableAirplanes(airplanes);
		System.out.println("--------------------------------------------------------------------------------");
		shouldReturn = showAvailableAirports(airports);
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------");

		/*
		 * If there was a problem when retrieving the airplanes or airports, we
		 * will return.
		 */
		if (shouldReturn) {
			return;
		}

		/*
		 * Go through and get each piece of information for creating a flight.
		 */
		while (true) {

			if (flight.getDepartureDate() == null) {
				shouldReturn = getDepartureDate(flight);
			} else if (flight.getDepartureAirportCode() == null) {
				shouldReturn = getDepartureAirportCode(flight, airports);
			} else if (flight.getDestinationAirportCode() == null) {
				shouldReturn = getDestinationAirportCode(flight, airports);
			} else if (flight.getCost() <= 0.0) {
				shouldReturn = getFlightCost(flight);
			} else if (flight.getAirplaneId() < 0) {
				shouldReturn = getAirplaneId(flight, airplanes);
			} else {
				createFlight(flight);
				shouldReturn = true;
			}

			if (shouldReturn) {
				return;
			}
		}

	}

	/**
	 * Displays a formatted list of all the airports in the system. They are
	 * sorted alphabetically from a to z.
	 * 
	 * @param storedAirports
	 *            Collection of airports in the system.
	 * @return True if the menu should be reshown. False if it should return.
	 */
	private boolean showAvailableAirports(Collection<String> storedAirports) {
		try {
			AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming.lookup(rmiUrl);
			Collection<String> airports = reserver.getAllAirports();

			System.out.println("AIRPORT(LOCATION) CODE");
			for (String airport : airports) {
				System.out.println(airport);
			}

			storedAirports.addAll(airports);
			return false;
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
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting retrieve store data, please try again later.");
			return true;
		}
	}

	/**
	 * Displays a formatted list of all the airplanes in the system. They are
	 * sorted alphabetically from a to z, first by departure code, then by
	 * destination code.
	 * 
	 * @param storedAirplanes
	 *            Collection of airports in the system.
	 * @return True if the menu should be reshown. False if it should return.
	 */
	private boolean showAvailableAirplanes(Collection<Airplane> storedAirplanes) {
		try {
			AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming.lookup(rmiUrl);
			Collection<Airplane> airplanes = reserver.getAllAirplanes();

			System.out.println("PLANE ID\tPLANE TYPE\t# SEATS");
			for (Airplane airplane : airplanes) {
				String airplaneString = airplane.getId() + "\t\t" + airplane.getType() + "\t\t"
						+ airplane.getNumSeats();
				System.out.println(airplaneString);
			}

			storedAirplanes.addAll(airplanes);
			return false;
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
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting retrieve store data, please try again later.");
			return true;
		}
	}

	/**
	 * Method that makes a call to the server to create a Flight. This method
	 * will show any errors that occur. It will also show a successful message
	 * if the flight was created.
	 * 
	 * @param flight
	 *            Flight to create
	 */
	private void createFlight(Flight flight) {
		try {
			AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming.lookup(rmiUrl);
			int flightId = reserver.createFlight(flight);
			System.out.println("");
			System.out.println("* Successfully added flight number " + flightId + ".");
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
			System.out.println("****** Flight Creation Failed");
			System.out.println("Validation Error - Please see message(s) below:");
			showErrorMessages(e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting to store data, please try again later.");
		}
	}

	/**
	 * This method hanldes the user's choice of the airplane Id that should be
	 * used for the flight. It will handle any invalid input. I also recognizes
	 * if the Id is not an Id available for them to choose. This method will set
	 * the airplaneId of the <code>flight</code> object.
	 * 
	 * @param flight
	 *            Object used to store the airplane Id.
	 * @param airplanes
	 *            Available airplane choices the user has to choose from.
	 * @return True if the same menu should be shown, or False if the previous
	 *         menu should be shown.
	 */
	private boolean getAirplaneId(Flight flight, Collection<Airplane> airplanes) {
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
					if (isExistingAirplane(Integer.parseInt(sAirplaneId), airplanes)) {
						flight.setAirplaneId(Integer.parseInt(sAirplaneId));
						return false;
					} else {
						System.out.println("Error: The provided airplane Id does not exist.");
						System.out
								.print("Please enter the airplane Id that should be used for the flight (see airplane Ids listed above), or R to return----> ");
					}
				} else {
					System.out
							.print(sAirplaneId
									+ " is an invalid Id. Please enter the airplane Id (a whole number) that should be used for the flight, or R to return----> ");
				}
			}

		}
	}

	/**
	 * Returns true if the provided airplaneId is in the Collection of
	 * <code>airplanes</code>. Otherwise returns false.
	 * 
	 * @param airplaneId
	 *            Airplane Id to check on.
	 * @param airplanes
	 *            Collection of airplanes to check against.
	 * @return True if the airplaneId is in the collection of airplanes.
	 */
	private boolean isExistingAirplane(int airplaneId, Collection<Airplane> airplanes) {
		for (Airplane airplane : airplanes) {
			if (airplaneId == airplane.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method handles the menu input from the user for the cost of the
	 * airplane, and all related invalid inputs.
	 * 
	 * @param flight
	 *            Flight object to set the cost of.
	 * @return True if the same menu should be shown to the user. False if the
	 *         previuos menu should be shown.
	 */
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

	/**
	 * This menu hanldes the user's input for the destination airport code, and
	 * all related invalid inputs. If the user chooses a code that doesn't exist
	 * in the provided <code>airports</code>, then an error message will be
	 * displayed.
	 * 
	 * @param flight
	 *            Flight object to store the destination airport code in.
	 * @param airports
	 *            Collection of airports the user must choose from.
	 * @return True if the same menu should be shown to the user. False if the
	 *         previous menu should be displayed.
	 */
	private boolean getDestinationAirportCode(Flight flight, Collection<String> airports) {
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
			} else if (isExistingAirport(destinationLocation, airports)) {
				flight.setDestinationAirportCode(destinationLocation);
				return false;
			} else {
				System.out.println("Error: The provided Destination Code does not exist.");
				System.out
						.print("Please enter Destination Location Code for the flight (see airport codes listed above), or R to return----> ");
			}

		}
	}

	/**
	 * This menu hanldes the user's input for the departure airport code, and
	 * all related invalid inputs. If the user chooses a code that doesn't exist
	 * in the provided <code>airports</code>, then an error message will be
	 * displayed.
	 * 
	 * @param flight
	 *            Flight object to store the departure airport code in.
	 * @param airports
	 *            Collection of airports the user must choose from.
	 * @return True if the same menu should be shown to the user. False if the
	 *         previous menu should be displayed.
	 */
	private boolean getDepartureAirportCode(Flight flight, Collection<String> airports) {
		System.out.print("Please enter Departure Location Code for the flight, or R to return----> ");

		while (true) {
			String departureLocation = this.readLine();

			if (departureLocation == null || departureLocation.length() < 1) {
				System.out.print("Error: Please enter Departure Location Code for the flight, or R to return----> ");
			} else if (departureLocation.equalsIgnoreCase("R")) {
				return true;
			} else if (isExistingAirport(departureLocation, airports)) {
				flight.setDepartureAirportCode(departureLocation);
				return false;
			} else {
				System.out.println("Error: The provided Departure Code does not exist.");
				System.out
						.print("Please enter Departure Location Code for the flight (see airport codes listed above), or R to return----> ");
			}

		}
	}

	/**
	 * Returns true if the provided departureLocation exists in the provided
	 * <code>airports</code>
	 * 
	 * @param departureLocation
	 *            Departure airport code.
	 * @param airports
	 *            Collection of airports the departureLocation should be
	 *            compared against.
	 * @return True if the provided departureLocation is in the provided
	 *         <code>airports</code>. Otherwise returns false.
	 */
	private boolean isExistingAirport(String departureLocation, Collection<String> airports) {
		for (String airport : airports) {
			if (airport.equalsIgnoreCase(departureLocation)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method handles the user's input for the departure date of a flight.
	 * It also handles all invalid input, including the wrong format for a date.
	 * 
	 * @param flight
	 *            Flight object to set the date for.
	 * @return True if the same menu should be shown to the user. False if the
	 *         previous menu should be displayed.
	 */
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

	/**
	 * This method displays the menu for creating an airport code. It also
	 * handles any invalid input and calling the server to create the new
	 * airport.
	 */
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
					AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming
							.lookup(rmiUrl);
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

	/**
	 * This method displays the menu for creating an airport in the system. It
	 * will also call the server to create the airport and handle any invalid
	 * input.
	 */
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
							AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming
									.lookup(rmiUrl);
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

	/**
	 * Utility method used to print out any validation errors the server
	 * returns.
	 * 
	 * @param e
	 *            Validation errors
	 */
	private void showErrorMessages(ValidationException e) {
		for (String errorMessage : e.getErrorMessages()) {
			System.out.println("-- " + errorMessage);
		}

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

	/**
	 * Utility method to determine if the provided currencyAmount is a
	 * {@link Double}. Returns true if it is, false if it isn't.
	 * 
	 * @param currencyAmount
	 *            Value to check.
	 * @return True if currenctyAmount is a {@link Double}, false if it isn't.
	 */
	private boolean isValidCurrency(String currencyAmount) {
		try {
			// Test if the value is actually a number
			new Double(currencyAmount);
			return true;
		} catch (NumberFormatException e) {
			// Ignore, this meants it isn't a double.
		}
		return false;
	}

	/**
	 * This method displays the Flight Search menu to the user.
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
	 * This method handles the user's input for the flight searching. If the
	 * user chooses the option to quite, this method can exit the application.
	 * 
	 * @param searchFilters
	 *            SearchFilters that should be set when the user enters valid
	 *            data.
	 * @return True if the flight menu should be displayed, False if the Main
	 *         Menu should be displayed.
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

	/**
	 * Used to display the currently set filters the user has entered. If no
	 * values have been entered, "NONE" will be displayed. Only the values
	 * entered will be displayed.
	 * 
	 * @param searchFilters
	 *            SearchFilters to display.
	 * @return String of the current SearchFilters.
	 */
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

	/**
	 * This method displays the menu for setting a departure code. It also
	 * handles the user's input and invalid input.
	 * 
	 * @param searchFilters
	 *            SearchFilters object to set the object to.
	 */
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

	/**
	 * This method displays the menu to the user that will set a destination
	 * airport code. It also handles any invalid input.
	 * 
	 * @param searchFilters
	 *            SearchFilters object to set the destination airport code to.
	 */
	private void setDestinationLocation(SearchFilters searchFilters) {
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

	/**
	 * This method displays the menu that allows the user to set the date of the
	 * trip/reservation. It also handles any invalid input.
	 * 
	 * @param searchFilters
	 *            SearchFilters object to set the date to.
	 */
	private void setDateOfTrip(SearchFilters searchFilters) {
		System.out.println("");
		System.out.println("**********************************");
		System.out.println("** Set Date of Reservation/Trip **");
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

	/**
	 * This method will run the search, using the provided searchFilters, to get
	 * back all flights that match the filter. It will then show the reservation
	 * menu to the user.
	 * 
	 * If the searchFilters is empty, then an error message will be displayed.
	 * If no flights were found matching the filter, then a message is
	 * displayed.
	 * 
	 * @param searchFilters
	 *            SearchFilters to use when searching for flights.
	 */
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

	/**
	 * This method will call the server to run the search. If any errors occur,
	 * or there are validation errors, messages will be shown describing what
	 * happened.
	 * 
	 * @param searchFilters
	 *            SearchFilter to run the search on.
	 * @return Collection of flights that match the provided searchFilters. If
	 *         no flights were found, or error occured, null will be returned.
	 */
	private Collection<Flight> runSearch(SearchFilters searchFilters) {
		try {
			AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming.lookup(rmiUrl);
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

	/**
	 * This method will display the menu for reserving a flight. It will also
	 * handle some input validation.
	 * 
	 * @param flights
	 *            Collection of flights that resulted from the search.
	 */
	private void reserveProcess(Collection<Flight> flights) {
		showFlights(flights);

		System.out.println("");
		System.out.println("************************");
		System.out.println("** Reserving a Flight **");
		System.out.println("    R - Return (no updates will occur)");

		StringBuffer sFlightIdBuf = new StringBuffer();
		StringBuffer sNumOfSeatsBuf = new StringBuffer();

		boolean shouldReturn = false;

		while (true) {
			if (sFlightIdBuf.length() < 1) {
				shouldReturn = getFlightId(sFlightIdBuf, flights);
			} else if (sNumOfSeatsBuf.length() < 1) {
				shouldReturn = getNumberOfSeatsToReserve(sNumOfSeatsBuf);
			} else {
				try {
					shouldReturn = createReservation(Integer.parseInt(sFlightIdBuf.toString()), Integer
							.parseInt(sNumOfSeatsBuf.toString()));
				} catch (ValidationException e) {
					System.out.println("You may choose to reserve another flight, or return and run a new search.");
					sFlightIdBuf = new StringBuffer();
					sNumOfSeatsBuf = new StringBuffer(0);
				}
			}

			if (shouldReturn) {
				return;
			}
		}

	}

	/**
	 * This method calls the server to make a flight reservation. If there are
	 * errors, they will be displayed to the user. If the reservation was
	 * successful, the Reservation information will be shown to the user.
	 * 
	 * @param flightId
	 *            Flight number to make the reservation for.
	 * @param numSeats
	 *            The number of seats to reserve.
	 * @return Always returns true to show the flight search menu. Only other
	 *         option is if an exception is thrown from a validation error.
	 * @throws ValidationException
	 *             Thrown if there are any validation errors with the input
	 *             provided.
	 */
	private boolean createReservation(int flightId, int numSeats) throws ValidationException {
		try {
			AirlineHeadquartersRemoteService reserver = (AirlineHeadquartersRemoteService) Naming.lookup(rmiUrl);
			Reservation reservation = reserver.createReservation(flightId, numSeats);
			Flight flight = reservation.getFlight();

			System.out.println("");
			System.out.println("* Successfully create reservation.");
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("Reservation #: " + reservation.getId());
			System.out.println("Number of Seats Reserved: " + reservation.getNumSeats());
			System.out.println("Flight #: " + flight.getId());
			System.out.println("Departing From Airport: " + flight.getDepartureAirportCode());
			System.out.println("Arriving in Airport: " + flight.getDestinationAirportCode());
			System.out.println("Flight Date: " + flight.getDepartureDate());
			System.out.println("Total Cost: $" + (flight.getCost() * reservation.getNumSeats()));
			System.out.println("--------------------------------------------------------------------------------");
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
			System.out.println("****** Reservation Creation Failed");
			System.out.println("Validation Error - Please see message(s) below:");
			showErrorMessages(e);
			throw e;
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.print("Error while attempting to store data, please try again later.");
		}
		return true;
	}

	/**
	 * This method handles the user input for the number of seats to reserve for
	 * a flight. It also handles any invalid input.
	 * 
	 * @param numOfSeatsBuf
	 *            Object used for this method to set the number of seats that
	 *            should be reserved.
	 * @return True if the reservation process should continue. False if it
	 *         should return.
	 */
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
					if (Integer.parseInt(sNumSeats) <= 0) {
						System.out
								.print("Error: Please enter the number of seats to reserve (the Id should be >= 1), or R to return----> ");
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

	/**
	 * This method handles the user's input for the flight number to make a
	 * reservation for. It also handles any invalid input.
	 * 
	 * @param sFlightIdBuf
	 *            Object to set the flight number to.
	 * @param flights
	 *            Flights the user has to choose from.
	 * @return True if the reservation process should continue. False if it
	 *         shouldn't.
	 */
	private boolean getFlightId(StringBuffer sFlightIdBuf, Collection<Flight> flights) {
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
						System.out
								.print("Error: Please enter the flight Id for the reservation (the Id should be >= 0), or R to return----> ");
					} else if (isFlightIdInFlights(Integer.parseInt(sFlightId), flights)) {
						sFlightIdBuf.append(sFlightId);
						return false;
					} else {
						System.out.println("Error: The flight Id provided is not one of the choices listed.");
						System.out
								.print("Please enter the flight Id for the reservation (the Ids are listed in the search results), or R to return----> ");
					}
				} else {
					System.out
							.print(sFlightId
									+ " is an invalid Id. Please enter the flight Id (a whole number) for the reservation, or R to return----> ");
				}
			}

		}
	}

	/**
	 * Returns true if the provided flightId exists in the collection of
	 * <code>flights</code>. This method is used to validate the user chose a
	 * flight number that resulted from their search.
	 * 
	 * @param flightId
	 *            Value to check on
	 * @param flights
	 *            Flights the flightId should be in.
	 * @return True if the flightId is in the collection of <code>flights</code>
	 */
	private boolean isFlightIdInFlights(int flightId, Collection<Flight> flights) {
		for (Flight flight : flights) {
			if (flightId == flight.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method will printout the provided collection of flights.
	 * 
	 * @param flights
	 *            Flights that will be printed out.
	 */
	private void showFlights(Collection<Flight> flights) {
		System.out.println("*****************");
		System.out.println("** Flight List **");
		System.out.println("(Ordered by Departing Airport and then by Destination Airport)");
		System.out.println("");
		System.out.println("DEPARTING\tDESTINATION\t\t\t\t\t\tAVAILABLE");
		System.out.println("AIRPORT\t\tAIRPORT\t\tDEPART DATE\tFLIGHT ID\tCOST\tSEATS");
		System.out.println("--------------------------------------------------------------------------------");

		for (Flight flight : flights) {
			String airplane = flight.getDepartureAirportCode() + "\t\t" + flight.getDestinationAirportCode() + "\t\t"
					+ flight.getDepartureDate() + "\t" + flight.getId() + "\t\t$" + flight.getCost() + "\t"
					+ flight.getAvailableSeats();
			System.out.println(airplane);
		}
		System.out.println("--------------------------------------------------------------------------------");

	}

	/**
	 * Helper method to read in the user's input.
	 * 
	 * @return Returns the user's input with no excess whitespace in the
	 *         beginning or end of the string.
	 */
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
	 * Main method to run from command line that will start up the client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new AirlineClient();
	}
}