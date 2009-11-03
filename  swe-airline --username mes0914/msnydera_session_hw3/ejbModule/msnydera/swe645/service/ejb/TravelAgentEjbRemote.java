/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.service.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import msnydera.swe645.domain.Customer;
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
	 * Returns all airports that are in the system.
	 * 
	 * @return All the airports.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airports.
	 */
	public Collection<String> getAllAirports() throws DataAccessException;

	/**
	 * Returns all customers that are in the system.
	 * 
	 * @return All the customers.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the customers.
	 */
	public Collection<Customer> getAllCustomers() throws DataAccessException;

	/**
	 * Returns all the reservations that are in the system.
	 * 
	 * @return All the reservations
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the reservations.
	 */
	public Collection<Reservation> getAllReservations() throws DataAccessException;

	/**
	 * Creates a customer in the system with the provided information.
	 * 
	 * @param customer
	 *            The customer to create.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided customer.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the customer.
	 * @return The created customer.
	 */
	public Customer createCustomer(Customer customer) throws ValidationException, DataAccessException;

	/**
	 * Cancels a reservation containing the provided reservationId
	 * 
	 * @param reservationId
	 *            The Id of the reservation to cancel
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the Id
	 * @throws DataAccessException
	 *             Thrown if there is an error when canceling the reservation.
	 * @return The reservation canceled.
	 */
	public Reservation cancelReservation(int reservationId) throws ValidationException, DataAccessException;

	/**
	 * Gets the reservation associated with the provided reservationId
	 * 
	 * @param reservationId
	 *            The Id of the reservation to get
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided Id.
	 * @throws DataAccessException
	 *             Thrown if there is an error when getting the reservation.
	 * @return The reservation.
	 */
	public Reservation getReservation(int reservationId) throws ValidationException, DataAccessException;
}
