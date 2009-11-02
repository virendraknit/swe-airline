/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.service;

import java.util.Collection;

import msnydera.swe645.domain.Airplane;
import msnydera.swe645.domain.Customer;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.exception.ValidationException;

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
	 * Returns all customers that are in the system.
	 * 
	 * @return All the customers.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the customers.
	 */
	public Collection<Customer> getAllCustomers() throws DataAccessException;

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
	 * Creates an customer in the system with the provided information.
	 * 
	 * @param customer
	 *            The customer to create.
	 * @return Customer The created customer
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided code.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the airport.
	 */
	public Customer createCustomer(Customer customer) throws ValidationException, DataAccessException;

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
	 * @param customerId
	 *            Customer the reservation is for.
	 * @param numSeats
	 *            The number of seats the reservation is for.
	 * @return The Reservation created.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided
	 *             information.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the reservation.
	 */
	public Reservation createReservation(int flightId, int customerId, int numSeats) throws ValidationException,
			DataAccessException;

	/**
	 * Cancels a reservation containing the provided reservationId
	 * 
	 * @param reservationId
	 *            The Id of the reservation to cancel
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided code.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the airport.
	 * @return Customer The created customer.
	 */
	public Reservation cancelReservation(int reservationId) throws ValidationException, DataAccessException;

}