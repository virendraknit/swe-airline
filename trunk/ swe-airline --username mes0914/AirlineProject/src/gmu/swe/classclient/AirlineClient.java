package gmu.swe.classclient;

import gmu.swe.domain.SearchFilters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
			System.out.println("    1 - Quit");
			System.out.println("    2 - Search for Flights");
			System.out.print("Please enter command (1-2)----> ");

			String userOption = this.readLine();
			char letterOption = '\0';

			// convert the input to a char
			if (userOption.length() > 1) {
				letterOption = '3';
			} else {
				letterOption = userOption.charAt(0);
			}

			// decide what to do, based on the input
			switch (letterOption) {
			case '1':
				// Quit the program
				bContinue = false;
				System.out.println("Quiting - Good Bye");
				break;
			case '2':
				// Search for flights
				searchFlights();
				break;
			case '3':
			default:
				System.out.println("Error: Please enter only a number from the Options.");
				break;
			}
		}
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
			System.out.println("    6 - Return to Main Menu");
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
				return false;
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
		System.out.println("****************************");
		System.out.println("** Set Departure Location **");
		showCurrentSearchFilters(searchFilters);
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter Departure Location, or R to return----> ");

		while (true) {
			String departureLocation = this.readLine();

			if (departureLocation == null || departureLocation.length() < 1) {
				System.out.print("Error: Please enter Departure Location, or R to return----> ");
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
		System.out.println("****************************");
		System.out.println("** Set Destination Location **");
		showCurrentSearchFilters(searchFilters);
		System.out.println("    R - Return (no updates will occur)");
		System.out.print("Please enter Destination Location, or R to return----> ");
		
		while (true) {

			String destinationLocation = this.readLine();

			if (destinationLocation == null || destinationLocation.length() < 1) {
				System.out.print("Error: Please enter Destination Location, or R to return----> ");
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
					System.out.print("'" + sDateOfTrip + "' is an invalid date. Please enter a Date in the format MM/DD/YYYY, or R to return----> ");
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