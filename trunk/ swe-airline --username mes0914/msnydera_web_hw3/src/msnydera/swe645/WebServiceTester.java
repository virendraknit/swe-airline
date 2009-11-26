package msnydera.swe645;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import msnydera.swe645.service.ejb.DataAccessException;
import msnydera.swe645.service.ejb.Flight;
import msnydera.swe645.service.ejb.SearchFilters;
import msnydera.swe645.service.ejb.TravelAgentEjb;
import msnydera.swe645.service.ejb.TravelAgentEjbServiceLocator;
import msnydera.swe645.service.ejb.ValidationException;

public class WebServiceTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		/ get a reference to the PersonManager service
//		PersonManagerImplServiceLocator service = new PersonManagerImplServiceLocator();
//		Person[] personArr = null;
//		try {
//			// Get the implementation of the PersonManager
//			PersonManagerImpl manager = service.getPersonManagerImplPort();
//			
//			// invoke a method on the web service
//			personArr = manager.findPeople();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 

		TravelAgentEjbServiceLocator service = new TravelAgentEjbServiceLocator();
		try {
			TravelAgentEjb ejbRef = service.getTravelAgentEjbPort();
			SearchFilters sf = new SearchFilters();
			
//			sf.setDepartureLocation("BWI");
			
			Calendar cal = Calendar.getInstance();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = sdf.parse("12/31/2009");
			
			cal.setTime(date);
			System.out.println("Calendar: " + cal.toString());
			
//			sf.setDateOfTrip()
			
			sf.setDateOfTrip(cal);
			
			Flight[] flights = ejbRef.search(sf);
			
			System.out.println("Successfully ran search");
			if(flights == null){
				System.out.println("Flights was null");
			}else{
				System.out.println("Number of Flights: " + flights.length);
			}
			
		} catch (ServiceException e) {
			
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
