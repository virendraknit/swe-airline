package gmu.swe.ui;

import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.rmi.AirlineTicketReserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
				return true;
			case '3':
				// create flight
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
				if (isNumber(sNumSeats)) {
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
							System.out.println("* Successfully added a " + airplaneType + " airplane with " + numSeats + " seats.");
							return;
						} catch (MalformedURLException e) {
							e.printStackTrace();
							System.out.print("Error: Airline server is currently down, please try again later.");
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

	private boolean isNumber(String numSeats) {
		try {
			// Test if the value is actually a number
			new Integer(numSeats);
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
			System.out.println("    4 - Run Search (requires departure, destination, or date to be set)");
			System.out.println("    5 - Reset Search Filters");
			System.out.println("    6 - Main Menu");
			System.out.println("    7 - Quit");
			System.out.print("Please enter command (1-7)----> ");

			shouldContinue = handleFlightSearchChoice(searchFilters);

		}

		// System.out.println("Email Listing: ");
		// System.out.println("");
		// System.out.println("  NAME      EMAIL ADDRESS");
		// System.out
		// .println("----------------------------------------------------------");
		// System.out.println("");
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
			System.out
					.println("Error: Running a search requires one or more of the following to be set - Departure, Destination, or Date.");
		}
		System.out.println("Not Implemented Yet");
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