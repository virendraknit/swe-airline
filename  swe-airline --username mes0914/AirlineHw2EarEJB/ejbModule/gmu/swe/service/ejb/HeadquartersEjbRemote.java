/*
 * Created by: Matt Snyder
 */
package gmu.swe.service.ejb;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;

import java.util.Collection;

import javax.ejb.Remote;

/**
 * Remote interface that provides an external entry point to the
 * AirlineHeadquartersService. This EJB only provides an access point to the
 * Headquarters related business.
 * 
 */
@Remote
public interface HeadquartersEjbRemote {
	/**
	 * Returns all airplanes that are in the system.
	 * 
	 * @return All the airplanes.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airplanes.
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException;

	/**
	 * Returns all airports that are in the system.
	 * 
	 * @return All the airports.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airports.
	 */
	public Collection<String> getAllAirports() throws DataAccessException;

	/**
	 * Returns all flights that are in the system.
	 * 
	 * @return All the flights.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airports.
	 */
	public Collection<Flight> getAllFlights() throws DataAccessException;

	/**
	 * Creates an airplane in the system with the provided information.
	 * 
	 * @param numberOfSeats
	 *            Number of seats that are on the airplane.
	 * @param airplaneType
	 *            The type of airplane (ex: 747, DC-10, etc.)
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided
	 *             parameters
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the airplane.
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException;

	/**
	 * Creates an airport in the system with the provided information.
	 * 
	 * @param airportCode
	 *            The unique airport code.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided code.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the airport.
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException;

	/**
	 * Creates a flight in the system with the provided information.
	 * 
	 * @param flight
	 *            Flight to create
	 * @return Unique Flight number that is associated with the created flight.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided
	 *             flight information.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating a flight.
	 */
	public int createFlight(Flight flight) throws ValidationException, DataAccessException;
}
