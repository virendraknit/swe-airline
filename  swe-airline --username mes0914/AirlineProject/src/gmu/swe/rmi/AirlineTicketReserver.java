package gmu.swe.rmi;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface AirlineTicketReserver extends Remote {
	public Collection<Flight> search(SearchFilters searchFilters) throws RemoteException;
	public Reservation reserveFlight(String flightNumber, int numberOfSeats) throws RemoteException;
}
