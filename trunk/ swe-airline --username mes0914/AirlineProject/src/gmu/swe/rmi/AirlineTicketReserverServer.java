package gmu.swe.rmi;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class AirlineTicketReserverServer extends UnicastRemoteObject implements AirlineTicketReserver {

	private static final long serialVersionUID = 5546301886910842040L;

	protected AirlineTicketReserverServer() throws RemoteException {
		super();
	}

	public Collection<Flight> search(SearchFilters searchFilters) throws RemoteException {
		System.out.println("Call to run search");
		return null;
	}

	public Reservation reserveFlight(String flightNumber, int numberOfSeats) throws RemoteException {
		System.out.println("Call to reserve flight");
		return null;
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException{
		AirlineTicketReserverServer server = new AirlineTicketReserverServer();
		Naming.rebind("/AirlineTicketReserverServer", server);
		System.out.println("Airline Server running and bound!");
	}
}
