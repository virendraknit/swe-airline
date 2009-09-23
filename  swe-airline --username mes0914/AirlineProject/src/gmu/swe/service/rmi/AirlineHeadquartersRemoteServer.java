package gmu.swe.service.rmi;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.AirlineHeadquartersService;
import gmu.swe.service.impl.AirlineHeadquartersServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class AirlineHeadquartersRemoteServer extends UnicastRemoteObject implements AirlineHeadquartersRemoteService {

	private static final long serialVersionUID = 5546301886910842040L;

	// Service that actually performs the business logic
	private AirlineHeadquartersService service;

	/**
	 * Initializes this.
	 * 
	 * @throws RemoteException
	 *             If an error occurs with the connection
	 */
	protected AirlineHeadquartersRemoteServer() throws RemoteException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#search(gmu.swe.domain.SearchFilters)
	 */
	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException,
			RemoteException {
		System.out.println("Running Search");
		return this.getService().search(searchFilters);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#createAirplane(int, java.lang.String)
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException,
			RemoteException {
		System.out.println("Creating Airplane");
		this.getService().createAirplane(numberOfSeats, airplaneType);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#createAirport(java.lang.String)
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException, RemoteException {
		System.out.println("Creating Airport");
		this.getService().createAirport(airportCode);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#createFlight(gmu.swe.domain.Flight)
	 */
	public int createFlight(Flight flight) throws ValidationException, DataAccessException, RemoteException {
		System.out.println("Creating Flight");
		return this.getService().createFlight(flight);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#createReservation(int, int)
	 */
	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException,
			RemoteException {
		System.out.println("Creating Reservation");
		return this.getService().createReservation(flightId, numSeats);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#getAllAirplanes()
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException, RemoteException {
		System.out.println("Retrieving All Airplanes");
		return this.getService().getAllAirplanes();
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.rmi.AirlineHeadquartersRemoteService#getAllAirports()
	 */
	public Collection<String> getAllAirports() throws DataAccessException, RemoteException {
		System.out.println("Retrieving All Airports");
		return this.getService().getAllAirports();
	}

	/**
	 * This method is used to get the correct business service implementation.
	 * This method makes this class loosely coupled in that someone could set a
	 * different implementation of a service by calling the setService() method.
	 * If no service is explicitly set, then this method will instantiate a
	 * known implementation.
	 * 
	 * @return AirlineHeadquartersService implementation
	 */
	public AirlineHeadquartersService getService() {
		if (this.service == null) {
			this.service = new AirlineHeadquartersServiceImpl();
		}
		return service;
	}

	/**
	 * Set the business service this class should use.
	 * 
	 * @param service
	 *            Business service to use.
	 */
	public void setService(AirlineHeadquartersService service) {
		this.service = service;
	}

	/**
	 * Starts the RMI server and binds it to localhost.
	 * 
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		AirlineHeadquartersRemoteServer server = new AirlineHeadquartersRemoteServer();
		Naming.rebind("/AirlineHeadquartersRemoteServer", server);
		System.out.println("Airline Server running and bound!");
	}
}
