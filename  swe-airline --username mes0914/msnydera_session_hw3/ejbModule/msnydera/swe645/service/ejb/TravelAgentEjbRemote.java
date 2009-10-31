/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.service.ejb;


import java.util.Collection;

import javax.ejb.Remote;

import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;

/**
 * Remote interface that provides an external entry point to the
 * AirlineHeadquartersService. This EJB only provides an access point to the
 * Travel Agent related business.
 * 
 */
@Remote
public interface TravelAgentEjbRemote {

	/**
	 * Searches for Flights based on the provided searchFilters.
	 * 
	 * @param searchFilters
	 *            Filters to used when searching for flights.
	 * @return Collection of Flights that match the provided searchFilters
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided
	 *             searchFilters
	 * @throws DataAccessException
	 *             Thrown if there is an error when searching for Flights.
	 */
	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException;

	/**
	 * Creates a reservation for on provided flight number, flightId, for the
	 * provided number of seats, numSeats.
	 * 
	 * @param flightId
	 *            Flight number the reservation should be created for.
	 * @param numSeats
	 *            The number of seats the reservation is for.
	 * @return The Reservation created.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided
	 *             information.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the reservation.
	 */
	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException;
	
	/**
	 * Returns all airports that are in the system.
	 * 
	 * @return All the airports.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airports.
	 */
	public Collection<String> getAllAirports() throws DataAccessException;
}
