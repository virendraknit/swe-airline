package gmu.swe.service.ejb;

import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.AirlineHeadquartersService;
import gmu.swe.service.impl.AirlineHeadquartersServiceImpl;
import gmu.swe.util.DbUtils;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 * Session Bean implementation class TravelAgentEjb
 */
@Stateless
public class TravelAgentEjb implements TravelAgentEjbRemote{
	private AirlineHeadquartersService service;

	@Resource (mappedName="java:/msnyderaDS")
	private DataSource ds;
	
	/**
	 * Default constructor.
	 */
	public TravelAgentEjb() {
	}

	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException {
		DbUtils.setDs(this.ds);
		return this.getService().createReservation(flightId, numSeats);
	}

	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException {
		return this.getService().search(searchFilters);
	}

	public Collection<String> getAllAirports() throws DataAccessException {
		return this.getService().getAllAirports();
	}
	
	public AirlineHeadquartersService getService() {
		if (this.service == null) {
			this.service = new AirlineHeadquartersServiceImpl();
		}

		return this.service;
	}

	public void setService(AirlineHeadquartersService service) {
		this.service = service;
	}

}
