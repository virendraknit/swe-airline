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

	private AirlineHeadquartersService service;

	protected AirlineHeadquartersRemoteServer() throws RemoteException {
		super();
	}

	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException,
			RemoteException {
		System.out.println("Running Search");
		return this.getService().search(searchFilters);
	}

	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException,
			RemoteException {
		System.out.println("Creating Airplane");
		this.getService().createAirplane(numberOfSeats, airplaneType);
	}

	public void createAirport(String airportCode) throws ValidationException, DataAccessException, RemoteException {
		System.out.println("Creating Airport");
		this.getService().createAirport(airportCode);
	}

	public int createFlight(Flight flight) throws ValidationException, DataAccessException, RemoteException {
		System.out.println("Creating Flight");
		return this.getService().createFlight(flight);
	}

	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException,
			RemoteException {
		System.out.println("Creating Reservation");
		return this.getService().createReservation(flightId, numSeats);
	}

	public Collection<Airplane> getAllAirplanes() throws DataAccessException, RemoteException {
		System.out.println("Retrieving All Airplanes");
		return this.getService().getAllAirplanes();
	}

	public Collection<String> getAllAirports() throws DataAccessException, RemoteException {
		System.out.println("Retrieving All Airports");
		return this.getService().getallAirports();
	}
	
	public AirlineHeadquartersService getService() {
		if (this.service == null) {
			this.service = new AirlineHeadquartersServiceImpl();
		}
		return service;
	}

	public void setService(AirlineHeadquartersService service) {
		this.service = service;
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		AirlineHeadquartersRemoteServer server = new AirlineHeadquartersRemoteServer();
		Naming.rebind("/AirlineHeadquartersRemoteServer", server);
		System.out.println("Airline Server running and bound!");
	}
}
