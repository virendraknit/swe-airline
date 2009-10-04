package gmu.swe.service.ejb;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;
import gmu.swe.service.AirlineHeadquartersService;
import gmu.swe.service.impl.AirlineHeadquartersServiceImpl;

import java.util.Collection;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class HeadquartersEjb
 */
@Stateless
public class HeadquartersEjb implements HeadquartersEjbRemote {
	private AirlineHeadquartersService service;

    /**
     * Default constructor. 
     */
    public HeadquartersEjb() {
    }

    /*
     * (non-Javadoc)
     * @see gmu.swe.service.ejb.HeadquartersEjbRemote#createAirplane(int, java.lang.String)
     */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException {
		this.getService().createAirplane(numberOfSeats, airplaneType);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#createAirport(java.lang.String)
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException {
		this.getService().createAirport(airportCode);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#createFlight(gmu.swe.domain.Flight)
	 */
	public int createFlight(Flight flight) throws ValidationException, DataAccessException {
		return this.getService().createFlight(flight);
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#getAllAirplanes()
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException {
		return this.getService().getAllAirplanes();
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#getAllAirports()
	 */
	public Collection<String> getAllAirports() throws DataAccessException {
		return this.getService().getAllAirports();
	}

	/*
	 * (non-Javadoc)
	 * @see gmu.swe.service.ejb.HeadquartersEjbRemote#getAllFlights()
	 */
	public Collection<Flight> getAllFlights() throws DataAccessException {
		return this.getService().getAllFlights();
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
