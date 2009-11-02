/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import msnydera.swe645.domain.Airplane;
import msnydera.swe645.domain.Airport;
import msnydera.swe645.domain.Customer;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;

/**
 * This is the Data Access Object (DAO) used for the database communication.
 * 
 */
public class AirlineHeadquartersJpaDao {
	private EntityManager entityManager;

	/**
	 * Default constructor used to set the EntityManager so this DAO can access
	 * the database.
	 * 
	 * @param entityManager
	 *            EntityManager to use to access the database.
	 */
	public AirlineHeadquartersJpaDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Returns all of the airplanes in the database.
	 * 
	 * @return All airplanes
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException {
		Collection<Airplane> airplanes = new ArrayList<Airplane>();

		Query query = this.entityManager.createQuery("from Airplane ORDER BY TYPE ASC");
		List<?> airplaneList = query.getResultList();

		for (Object airplane : airplaneList) {
			Airplane storedAirplane = (Airplane) airplane;
			airplanes.add(storedAirplane);
		}

		this.entityManager.clear();

		return airplanes;
	}

	/**
	 * Returns all of the airports in the database.
	 * 
	 * @return All of the airports.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Collection<String> getAllAirports() throws DataAccessException {
		Collection<String> airports = new ArrayList<String>();

		Query query = this.entityManager.createQuery("from Airport");
		List<?> airportList = query.getResultList();

		for (Object airport : airportList) {
			Airport storedAirport = (Airport) airport;
			airports.add(storedAirport.getAirportCode());
		}

		this.entityManager.clear();

		return airports;
	}

	/**
	 * Returns all of the flights in the database.
	 * 
	 * @return All of the flights.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Collection<Flight> getAllFlights() throws DataAccessException {

		Collection<Flight> flights = new ArrayList<Flight>();

		Query query = this.entityManager.createQuery("from Flight ORDER BY DEPARTURE_DATE");
		List<?> flightList = query.getResultList();

		for (Object flight : flightList) {
			Flight storedFlight = (Flight) flight;
			flights.add(storedFlight);
		}

		this.entityManager.clear();

		return flights;
	}

	/**
	 * Returns all of the customer in the database.
	 * 
	 * @return All of the customers.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Collection<Customer> getAllCustomers() throws DataAccessException {

		Collection<Customer> customers = new ArrayList<Customer>();

		Query query = this.entityManager.createQuery("from Customer ORDER BY NAME");
		List<?> customerList = query.getResultList();

		for (Object customer : customerList) {
			Customer storedCustomer = (Customer) customer;
			customers.add(storedCustomer);
		}

		this.entityManager.clear();

		return customers;
	}

	/**
	 * Returns the Reservation for the provided reservationId.
	 * 
	 * @param reservationId
	 *            Id of the reservation
	 * @return The Reservation for the provided reservationId.
	 */
	public Reservation getReservation(int reservationId) {
		Reservation reservation = this.entityManager.find(Reservation.class, reservationId);
		
		return reservation;
	}

	/**
	 * Creates an airplane in the database. This method assumes numberOfSeats is
	 * > 0, and the airplaneType is not null or empty string.
	 * 
	 * @param numberOfSeats
	 *            Number of seats the aiplane will have
	 * @param airplaneType
	 *            The type of airplane (ex: 747, DC-10, etc.)
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public void createAirplane(int numberOfSeats, String airplaneType) throws DataAccessException {
		Airplane airplane = new Airplane();
		airplane.setNumSeats(numberOfSeats);
		airplane.setType(airplaneType.toUpperCase());

		this.entityManager.persist(airplane);
		this.entityManager.clear();
	}

	/**
	 * Creates an airport in the database with the provided airportCode. This
	 * value must not already exist in the database.
	 * 
	 * @param airportCode
	 *            Code of the airport
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public void createAirport(String airportCode) throws DataAccessException {
		Airport airport = new Airport();
		airport.setAirportCode(airportCode);

		this.entityManager.persist(airport);
	}

	/**
	 * Creates a customer in the database with the provided customer object. The
	 * ID of the customer must not exist in the database.
	 * 
	 * @param customer
	 *            Customer to create
	 * @return Customer The created customer
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Customer createCustomer(Customer customer) throws DataAccessException {
		this.entityManager.persist(customer);

		return customer;
	}

	/**
	 * Creates a flight in the database.
	 * 
	 * @param flight
	 *            Flight to create.
	 * @return The flight that was created.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Flight createFlight(Flight flight) throws DataAccessException {
		Airplane airplane = this.entityManager.find(Airplane.class, flight.getAirplane().getId());
		this.entityManager.clear();

		flight.setAvailableSeats(airplane.getNumSeats());

		this.entityManager.persist(flight);

		this.entityManager.clear();

		return flight;
	}

	/**
	 * Creates a reservation on a flight.
	 * 
	 * @param flightId
	 *            Flight Id the reservation should be made for.
	 * @param customerId
	 *            Customer Id of the customer the reservation is for.
	 * @param numSeats
	 *            Number of seats the reservation is for.
	 * @return The Reservation that was created.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Reservation createReservation(int flightId, int customerId, int numSeats) throws DataAccessException {
		Flight flight = this.entityManager.find(Flight.class, flightId);
		Customer customer = this.entityManager.find(Customer.class, customerId);

		Reservation reservation = new Reservation();
		reservation.setNumSeats(numSeats);
		reservation.setFlight(flight);
		reservation.setCustomer(customer);
		reservation.setStatus("RESERVED");

		this.entityManager.persist(reservation);

		flight.setAvailableSeats(flight.getAvailableSeats() - numSeats);

		this.entityManager.merge(flight);

		return reservation;
	}

	/**
	 * Cancels the reservation corresponding with the provided reservationId.
	 * 
	 * @param reservationId
	 *            Reservation to cancel
	 * @return Canceled reservation.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Reservation cancelReservation(int reservationId) throws DataAccessException {
		Reservation reservation = this.entityManager.find(Reservation.class, reservationId);
		reservation.setStatus("CANCELED");
		Flight flight = reservation.getFlight();
		flight.setAvailableSeats(flight.getAvailableSeats() + reservation.getNumSeats());

		this.entityManager.merge(reservation);
		this.entityManager.merge(flight);

		return reservation;
	}

	/**
	 * Returns true/false on whether or not the provided airport code exists in
	 * the database. The string is case-insensitive.
	 * 
	 * @param airportCode
	 *            Airport code to check on.
	 * @return True/False if the provided airport code exists in the database.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public boolean doesAirportExist(String airportCode) throws DataAccessException {
		Airport airport = this.entityManager.find(Airport.class, airportCode.toUpperCase());
		this.entityManager.clear();

		return airport != null;
	}

	/**
	 * Returns true/false on whether or not the airplaneId provided is an
	 * airplane in the database.
	 * 
	 * @param airplaneId
	 *            Airplane Id to check on.
	 * @return True/False if the provided airplane Id corresponds with an
	 *         existing airplane in the database.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public boolean doesAirplaneExist(int airplaneId) throws DataAccessException {
		Airplane airplane = this.entityManager.find(Airplane.class, airplaneId);
		this.entityManager.clear();

		return airplane != null;
	}

	/**
	 * Returns true/false on whether or not the reservationId provided is a
	 * reservation in the database.
	 * 
	 * @param reservationId
	 *            Reservation Id to check on.
	 * @return True/False if the provided reservation Id corresponds with an
	 *         existing reservation in the database.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public boolean doesReservationExist(int reservationId) throws DataAccessException {
		Reservation reservation = this.entityManager.find(Reservation.class, reservationId);

		return reservation != null;
	}

	/**
	 * Returns true/false on whether or not the customerId provided is a
	 * customer in the database.
	 * 
	 * @param customerId
	 *            Customer Id to check on.
	 * @return True/False if the provided customer Id corresponds with an
	 *         existing customer in the database.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public boolean doesCustomerExist(int customerId) {
		Customer customer = this.entityManager.find(Customer.class, customerId);

		return customer != null;
	}

	/**
	 * Returns true/false on whether the provided flightId corresponds with an
	 * existing Flight in the database.
	 * 
	 * @param flightId
	 *            Flight Id to check
	 * @return True/False whether the flight exists.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public boolean doesFlightExist(int flightId) throws DataAccessException {
		Flight flight = this.entityManager.find(Flight.class, flightId);
		this.entityManager.clear();

		return flight != null;
	}

	/**
	 * Returns the number of available seats on the Flight that has an Id of
	 * flightId.
	 * 
	 * @param flightId
	 *            Id of the flight.
	 * @return The number of available seats for the flight.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public int getNumberOfAvailableSeats(int flightId) throws DataAccessException {
		Flight flight = this.entityManager.find(Flight.class, flightId);
		this.entityManager.clear();

		return flight.getAvailableSeats();
	}

	/**
	 * Searches on the flights that match the provided searchFilters. If the
	 * searchFilters.getDateOfTrip is null, this method will search on all
	 * flights that are today or later (i.e. it won't search on past flights.)
	 * This method will return a flight even if it has 0 available seats.
	 * 
	 * @param searchFilters
	 *            Filters to apply to the flight search.
	 * @return Collection of Flights that match the provided search filters. If
	 *         no Flights match the provided filter, then null is returned.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Collection<Flight> search(SearchFilters searchFilters) throws DataAccessException {
		String sQuery = generateJpaSearchQuery(searchFilters);
		Query query = this.entityManager.createQuery(sQuery);

		if (searchFilters.getDateOfTrip() != null) {
			query.setParameter("departDate", new Date(searchFilters.getDateOfTrip().getTime()));
		} else {
			query.setParameter("departDate", new Date(new java.util.Date().getTime()));
		}

		List<?> flightList = query.getResultList();

		Collection<Flight> flights = new ArrayList<Flight>();

		for (Object flight : flightList) {
			Flight storedFlight = (Flight) flight;
			flights.add(storedFlight);
		}

		this.entityManager.clear();

		return flights;
	}

	/**
	 * Utility method used to create the search string based on the filters
	 * provided to run a search for flights on.
	 * 
	 * @param searchFilters
	 *            Filters to use when searching for flights.
	 * @return Query string to use to search for flights.
	 */
	private String generateJpaSearchQuery(SearchFilters searchFilters) {
		String query = "select f from Flight AS f ";
		String clause = "";

		if (searchFilters.getDateOfTrip() != null) {
			clause += " WHERE f.departureDate = :departDate";
		} else {
			/*
			 * If no date is provided, we still want to limit the results
			 * because we don't want to return flights that have already
			 * occurred.
			 */
			clause += " WHERE f.departureDate >= :departDate";
		}

		if (searchFilters.getDepartureLocation() != null && !searchFilters.getDepartureLocation().trim().equals("")) {
			clause += " AND f.departureAirport = '" + searchFilters.getDepartureLocation() + "'";
		}
		if (searchFilters.getDestinationLocation() != null && !searchFilters.getDestinationLocation().trim().equals("")) {
			clause += " AND f.destinationAirport = '" + searchFilters.getDestinationLocation() + "'";
		}

		return query + clause + " ORDER BY f.departureAirport, f.destinationAirport ASC";
	}
}
