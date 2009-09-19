package gmu.swe.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class StartAirportServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AirlineTicketReserverServer server = new AirlineTicketReserverServer();
			Naming.rebind("/AirlineTicketReserverServer", server);
			System.out.println("Airline Server running and bound!");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
