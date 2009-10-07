/*
 * Created by: Matt Snyder
 */
package gmu.swe.service.ejb;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.AirlineHeadquartersService;
import gmu.swe.service.impl.AirlineHeadquartersServiceImpl;

import java.util.Collection;

import javax.ejb.Stateless;

/**
 * Session Bean implementation of the Remote TravelAgentEJB. This class is
 * basically a delegate object for the AirlineHeadquartersService business
 * implementation. This EJB is only used to provide an external communication
 * point. The functionality provided here is for the Travel Agent business.
 */
@Stateless
public class TravelAgentEjb implements TravelAgentEjbRemote {
	private AirlineHeadquartersService service;

	/**
	 * Default constructor.
	 */
	public TravelAgentEjb() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gmu.swe.service.ejb.TravelAgentEjbRemote#createReservation(int, int)
	 */
	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException {
		return this.getService().createReservation(flightId, numSeats);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gmu.swe.service.ejb.TravelAgentEjbRemote#search(gmu.swe.domain.SearchFilters
	 * )
	 */
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

	/**
	 * 
	 * @return The service implementation to use.
	 */
	public AirlineHeadquartersService getService() {
		if (this.service == null) {
			this.service = new AirlineHeadquartersServiceImpl();
		}

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
