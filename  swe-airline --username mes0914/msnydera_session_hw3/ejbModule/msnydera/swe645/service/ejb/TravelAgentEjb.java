/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.service.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import msnydera.swe645.domain.Customer;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;
import msnydera.swe645.service.AirlineHeadquartersService;
import msnydera.swe645.service.impl.AirlineHeadquartersServiceImpl;

/**
 * Session Bean implementation of the Remote TravelAgentEJB. This class is
 * basically a delegate object for the AirlineHeadquartersService business
 * implementation. This EJB is only used to provide an external communication
 * point. The functionality provided here is for the Travel Agent business.
 */
@Stateless
@WebService
//@SecurityDomain("other")
//@RolesAllowed( { "admin", "agent" })
public class TravelAgentEjb implements TravelAgentEjbRemote {
	private AirlineHeadquartersService service;

	@PersistenceContext(unitName = "msnyderaPersistence")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public TravelAgentEjb() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.TravelAgentEjbRemote#createReservation(int, int, int)
	 */
	public Reservation createReservation(int flightId, int customerId, int numSeats) throws ValidationException, DataAccessException {
		return this.getService().createReservation(flightId, customerId, numSeats);
	}

	/*
	 * (non-Javadoc)
	 * @see msnydera.swe645.service.ejb.TravelAgentEjbRemote#cancelReservation(int)
	 */
	public Reservation cancelReservation(int reservationId) throws ValidationException, DataAccessException {
		return this.getService().cancelReservation(reservationId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see msnydera.swe645.service.ejb.TravelAgentEjbRemote#getReservation(int)
	 */
	public Reservation getReservation(int reservationId) throws ValidationException, DataAccessException {
		return this.getService().getReservation(reservationId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gmu.swe.service.ejb.TravelAgentEjbRemote#search(gmu.swe.domain.SearchFilters
	 * )
	 */
	@WebMethod
	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException {
		return this.getService().search(searchFilters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.TravelAgentEjbRemote#getAllAirports()
	 */
	public Collection<String> getAllAirports() throws DataAccessException {
		return this.getService().getAllAirports();
	}
	
	/*
	 * (non-Javadoc)
	 * @see msnydera.swe645.service.ejb.TravelAgentEjbRemote#getAllCustomers()
	 */
	public Collection<Customer> getAllCustomers() throws DataAccessException {
		return this.getService().getAllCustomers();
	}
	
	/*
	 * (non-Javadoc)
	 * @see msnydera.swe645.service.ejb.TravelAgentEjbRemote#getAllReservations()
	 */
	public Collection<Reservation> getAllReservations() throws DataAccessException {
		return this.getService().getAllReservations();
	}

	/*
	 * (non-Javadoc)
	 * @see msnydera.swe645.service.ejb.TravelAgentEjbRemote#createCustomer(msnydera.swe645.domain.Customer)
	 */
	public Customer createCustomer(Customer customer) throws ValidationException, DataAccessException {
		return this.getService().createCustomer(customer);
	}
	
	/**
	 * 
	 * @return The service implementation to use.
	 */
	public AirlineHeadquartersService getService() {
		this.service = new AirlineHeadquartersServiceImpl(this.entityManager);

		return this.service;
	}

	/**
	 * Sets the service implementation to use. This method would be used when
	 * applying dependency injection principles.
	 * 
	 * @param service
	 *            Service implementation to use.
	 */
	public void setService(AirlineHeadquartersService service) {
		this.service = service;
	}
}
