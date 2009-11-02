/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import msnydera.swe645.domain.Airplane;
import msnydera.swe645.domain.Airport;
import msnydera.swe645.domain.Flight;
import msnydera.swe645.domain.Reservation;
import msnydera.swe645.domain.SearchFilters;
import msnydera.swe645.exception.DataAccessException;
import msnydera.swe645.util.DbUtils;

/**
 * This is the Data Access Object (DAO) used for the database communication.
 * 
 */
public class AirlineHeadquartersJpaDao {
	private EntityManager entityManager;
	
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
			Airplane storedAirplane = (Airplane)airplane;
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
			Airport storedAirport = (Airport)airport;
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
			Flight storedFlight = (Flight)flight;
			flights.add(storedFlight);
		}
		
		this.entityManager.clear();
		
		return flights;
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

//	/**
//	 * Creates a flight in the database.
//	 * 
//	 * @param flight
//	 *            Flight to create.
//	 * @throws DataAccessException
//	 *             Thrown if a problem occurs while communicating with the
//	 *             database.
//	 */
//	private void createSingleFlight(Flight flight) throws DataAccessException {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//
//		try {
//			conn = DbUtils.getConnection();
//
//			stmt = conn
//					.prepareStatement("insert into flight (DEPARTURE_DATE, DEPARTURE_AIRPORT_CODE, DESTINATION_AIRPORT_CODE, "
//							+ "COST, AIRPLANE_ID, AVAILABLE_SEATS) values (?, ?, ?, ?, ?, (select num_seats from airplane where ID = ?))");
//			stmt.setDate(1, new Date(flight.getDepartureDate().getTime()));
//			stmt.setString(2, flight.getDepartureAirport().getAirportCode().toUpperCase());
//			stmt.setString(3, flight.getDestinationAirport().getAirportCode().toUpperCase());
//			stmt.setDouble(4, flight.getCost());
//			stmt.setInt(5, flight.getAirplane().getId());
//			stmt.setInt(6, flight.getAirplane().getId());
//
//			stmt.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DataAccessException(e.getMessage(), e);
//		} finally {
//			closeDbObjects(stmt, conn);
//		}
//	}

	/**
	 * Creates a reservation on a flight.
	 * 
	 * @param flightId
	 *            Flight Id the reservation should be made for.
	 * @param numSeats
	 *            Number of seats the reservation is for.
	 * @return The Reservation that was created.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Reservation createReservation(int flightId, int numSeats) throws DataAccessException {
		Flight flight = this.entityManager.find(Flight.class, flightId);
		
		Reservation reservation = new Reservation();
		reservation.setNumSeats(numSeats);
		reservation.setFlight(flight);
		
		this.entityManager.persist(reservation);
		
		flight.setAvailableSeats(flight.getAvailableSeats() - numSeats);
		
		this.entityManager.merge(flight);
		
//		this.entityManager.clear();
		
		return reservation;
		
//		Connection conn = null;
//		PreparedStatement stmt = null;
//
//		try {
//			conn = DbUtils.getConnection();
//
//			stmt = conn.prepareStatement("insert into RESERVATION (FLIGHT_ID, NUM_SEATS) values (?, ?)");
//			stmt.setInt(1, flightId);
//			stmt.setInt(2, numSeats);
//
//			stmt.executeUpdate();
//
//			stmt = conn.prepareStatement("update FLIGHT set AVAILABLE_SEATS = AVAILABLE_SEATS - ? where ID = ?");
//			stmt.setInt(1, numSeats);
//			stmt.setInt(2, flightId);
//
//			stmt.executeUpdate();
//
//			return getLastReservationAdded(flightId);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DataAccessException(e.getMessage(), e);
//		} finally {
//			closeDbObjects(stmt, conn);
//		}
	}

	/**
	 * Returns the Id of the last added reservation to the database.
	 * 
	 * @param flightId
	 *            Flight Id the reservation was made for.
	 * @return The last Reservation that was made.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	protected Reservation getLastReservationAdded(int flightId) throws DataAccessException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.createStatement();

			// Get Reservation
			ResultSet rs = stmt.executeQuery("select ID, NUM_SEATS from RESERVATION ORDER BY ID DESC");
			rs.next();

			Reservation reservation = new Reservation();
			reservation.setId(rs.getInt(1));
			reservation.setNumSeats(rs.getInt(2));

			reservation.setFlight(this.getFlight(flightId));

			return reservation;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

	/**
	 * Returns the Flight that has the Id, flightId. This method assumes
	 * flightId is a valid, existing Id.
	 * 
	 * @param flightId
	 *            Id of the Flight to get.
	 * @return The Flight corresponding with the provided flightId.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	protected Flight getFlight(int flightId) throws DataAccessException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("select * from FLIGHT where ID = ? ORDER BY ID DESC");
			stmt.setInt(1, flightId);

			ResultSet rs = stmt.executeQuery();
			rs.next();

			Flight flight = new Flight();
			flight.setId(rs.getInt(1));
			flight.setDepartureDate(rs.getDate(2));
			
			Airport airport = new Airport();
			airport.setAirportCode(rs.getString(3));
			flight.setDepartureAirport(airport);
			
			airport = new Airport();
			airport.setAirportCode(rs.getString(4));
			
			flight.setDestinationAirport(airport);
			flight.setCost(rs.getDouble(5));
			
			Airplane airplane = new Airplane();
			airplane.setId(rs.getInt(6));
			flight.setAirplane(airplane);
			
			flight.setAvailableSeats(rs.getInt(7));

			return flight;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

//	/**
//	 * Returns the flight Id of the last flight added.
//	 * 
//	 * @return Id of the last added Flight
//	 * @throws DataAccessException
//	 *             Thrown if a problem occurs while communicating with the
//	 *             database.
//	 */
//	private int getLastAddedFlightId() throws DataAccessException {
//		Connection conn = null;
//		Statement stmt = null;
//
//		try {
//			conn = DbUtils.getConnection();
//
//			stmt = conn.createStatement();
//
//			ResultSet rs = stmt.executeQuery("select ID from FLIGHT ORDER BY ID DESC");
//			rs.next();
//			return rs.getInt(1);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DataAccessException(e.getMessage(), e);
//		} finally {
//			closeDbObjects(stmt, conn);
//		}
//	}

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
			Flight storedFlight = (Flight)flight;
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
	
//	/**
//	 * Utility method used to create the search string based on the filters
//	 * provided to run a search for flights on.
//	 * 
//	 * @param searchFilters
//	 *            Filters to use when searching for flights.
//	 * @return Query string to use to search for flights.
//	 */
//	private String generateSearchQuery(SearchFilters searchFilters) {
//		String query = "select * from FLIGHT ";
//		String clause = "";
//
//		if (searchFilters.getDateOfTrip() != null) {
//			clause += " WHERE DEPARTURE_DATE = ?";
//		} else {
//			/*
//			 * If no date is provided, we still want to limit the results
//			 * because we don't want to return flights that have already
//			 * occurred.
//			 */
//			clause += " WHERE DEPARTURE_DATE >= ?";
//		}
//
//		if (searchFilters.getDepartureLocation() != null && !searchFilters.getDepartureLocation().trim().equals("")) {
//			clause += " AND DEPARTURE_AIRPORT_CODE = '" + searchFilters.getDepartureLocation() + "'";
//		}
//		if (searchFilters.getDestinationLocation() != null && !searchFilters.getDestinationLocation().trim().equals("")) {
//			clause += " AND DESTINATION_AIRPORT_CODE = '" + searchFilters.getDestinationLocation() + "'";
//		}
//
//		return query + clause + " ORDER BY DEPARTURE_AIRPORT_CODE, DESTINATION_AIRPORT_CODE ASC";
//	}

	/**
	 * Utility method used to close our the statement and connection.
	 * 
	 * @param stmt
	 *            Statement to close
	 * @param conn
	 *            Connection to close
	 */
	private void closeDbObjects(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// Intentially do nothing
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// Intentially do nothing
			}
		}
	}

//	/**
//	 * Utility method to create an EntityManager from the provided factory
//	 * obejct, and sets the flush mode to COMMIT for the entity manager.
//	 * 
//	 * @param factory
//	 *            EntityManagerFactory to create an EntityManager from
//	 * @return EntityManager with the flush mode set to COMMIT.
//	 */
//	private EntityManager getConfiguredManager(EntityManagerFactory factory) {
//		EntityManager entityManager = factory.createEntityManager();
//		entityManager.setFlushMode(FlushModeType.COMMIT);
//		return entityManager;
//	}
}
