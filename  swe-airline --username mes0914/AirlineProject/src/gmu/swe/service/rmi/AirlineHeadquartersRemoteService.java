package gmu.swe.service.rmi;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.exception.ValidationException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface AirlineHeadquartersRemoteService extends Remote {
	/**
	 * Returns all airplanes that are in the system.
	 * 
	 * @return All the airplanes.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airplanes.
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException, RemoteException;

	/**
	 * Returns all airports that are in the system.
	 * 
	 * @return All the airports.
	 * @throws DataAccessException
	 *             Thrown if there is an error when retrieving the airports.
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public Collection<String> getAllAirports() throws DataAccessException, RemoteException;

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
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public Collection<Flight> search(SearchFilters searchFilters) throws ValidationException, DataAccessException,
			RemoteException;

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
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws ValidationException, DataAccessException,
			RemoteException;

	/**
	 * Creates an airport in the system with the provided information.
	 * 
	 * @param airportCode
	 *            The unique airport code.
	 * @throws ValidationException
	 *             Thrown if there are validation errors with the provided code.
	 * @throws DataAccessException
	 *             Thrown if there is an error when creating the airport.
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public void createAirport(String airportCode) throws ValidationException, DataAccessException, RemoteException;

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
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public int createFlight(Flight flight) throws ValidationException, DataAccessException, RemoteException;

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
	 * @throws RemoteException
	 *             Thrown if there is a problem with the server.
	 */
	public Reservation createReservation(int flightId, int numSeats) throws ValidationException, DataAccessException,
			RemoteException;
}
