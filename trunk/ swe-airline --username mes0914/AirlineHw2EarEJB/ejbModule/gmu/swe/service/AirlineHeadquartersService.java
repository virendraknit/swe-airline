/*
 * Created by: Matt Snyder
 */
package gmu.swe.service;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;

import java.util.Collection;

/**
 * Business service that defines methods that should be available to be called.
 * This is meant to be a generic interface.
 * 
 */
public interface AirlineHeadquartersService {

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
	 * @return The Flight that was created.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided
	 *             flight information.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating a flight.
	 */
	public Flight createFlight(Flight flight) throws ValidationException, DataAccessException;

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

}