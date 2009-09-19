package gmu.swe.rmi;

import gmu.swe.domain.SearchFilters;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiTester {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String url = "/AirlineTicketReserverServer";
		AirlineTicketReserver reserver = (AirlineTicketReserver)Naming.lookup(url);
		
		reserver.search(new SearchFilters());
		reserver.reserveFlight("flight_num", 4);
	}

}
