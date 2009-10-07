/*
 * Created by: Matt Snyder
 */
package gmu.swe.dao;

import gmu.swe.domain.Airplane;
import gmu.swe.domain.Flight;
import gmu.swe.domain.Reservation;
import gmu.swe.domain.SearchFilters;
import gmu.swe.exception.DataAccessException;
import gmu.swe.util.DbUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is the Data Access Object (DAO) used for the database communication.
 * 
 */
public class AirlineHeadquartersDao {

	/**
	 * Returns all of the airplanes in the database.
	 * 
	 * @return All airplanes
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public Collection<Airplane> getAllAirplanes() throws DataAccessException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from AIRPLANE ORDER BY TYPE ASC");

			Collection<Airplane> airplanes = new ArrayList<Airplane>();
			while (rs.next()) {
				Airplane airplane = new Airplane();
				airplane.setId(rs.getInt(1));
				airplane.setNumSeats(rs.getInt(2));
				airplane.setType(rs.getString(3));

				airplanes.add(airplane);
			}

			return airplanes;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from AIRPORT ORDER BY CODE ASC");

			Collection<String> airports = new ArrayList<String>();
			while (rs.next()) {
				airports.add(rs.getString(1));
			}

			return airports;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from FLIGHT");

			Collection<Flight> flights = new ArrayList<Flight>();
			while (rs.next()) {
				if (flights == null) {
					flights = new ArrayList<Flight>();
				}

				Flight flight = new Flight();
				flight.setId(rs.getInt(1));
				flight.setDepartureDate(rs.getDate(2));
				flight.setDepartureAirportCode(rs.getString(3));
				flight.setDestinationAirportCode(rs.getString(4));
				flight.setCost(rs.getDouble(5));
				flight.setAirplaneId(rs.getInt(6));
				flight.setAvailableSeats(rs.getInt(7));

				flights.add(flight);
			}

			return flights;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("insert into airplane (num_seats, type) values (?, ?)");
			stmt.setInt(1, numberOfSeats);
			stmt.setString(2, airplaneType.toUpperCase());

			stmt.executeUpdate();
			// conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("insert into airport (code) values (?)");
			stmt.setString(1, airportCode.toUpperCase());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		createSingleFlight(flight);
		int flightId = getLastAddedFlightId();
		Flight savedFlight = getFlight(flightId);

		return savedFlight;
	}

	/**
	 * Creates a flight in the database.
	 * 
	 * @param flight
	 *            Flight to create.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	private void createSingleFlight(Flight flight) throws DataAccessException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn
					.prepareStatement("insert into flight (DEPARTURE_DATE, DEPARTURE_AIRPORT_CODE, DESTINATION_AIRPORT_CODE, "
							+ "COST, AIRPLANE_ID, AVAILABLE_SEATS) values (?, ?, ?, ?, ?, (select num_seats from airplane where ID = ?))");
			stmt.setDate(1, new Date(flight.getDepartureDate().getTime()));
			stmt.setString(2, flight.getDepartureAirportCode().toUpperCase());
			stmt.setString(3, flight.getDestinationAirportCode().toUpperCase());
			stmt.setDouble(4, flight.getCost());
			stmt.setInt(5, flight.getAirplaneId());
			stmt.setInt(6, flight.getAirplaneId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

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
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("insert into RESERVATION (FLIGHT_ID, NUM_SEATS) values (?, ?)");
			stmt.setInt(1, flightId);
			stmt.setInt(2, numSeats);

			stmt.executeUpdate();

			stmt = conn.prepareStatement("update FLIGHT set AVAILABLE_SEATS = AVAILABLE_SEATS - ? where ID = ?");
			stmt.setInt(1, numSeats);
			stmt.setInt(2, flightId);

			stmt.executeUpdate();

			return getLastReservationAdded(flightId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
			flight.setDepartureAirportCode(rs.getString(3));
			flight.setDestinationAirportCode(rs.getString(4));
			flight.setCost(rs.getDouble(5));
			flight.setAirplaneId(rs.getInt(6));
			flight.setAvailableSeats(rs.getInt(7));

			return flight;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

	/**
	 * Returns the flight Id of the last flight added.
	 * 
	 * @return Id of the last added Flight
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	private int getLastAddedFlightId() throws DataAccessException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select ID from FLIGHT ORDER BY ID DESC");
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

	/**
	 * Returns true/false on whether or not the provided airport code exists in
	 * the database. The string is case-insensitive.
	 * 
	 * @param destinationAirportCode
	 *            Airport code to check on.
	 * @return True/False if the provided airport code exists in the database.
	 * @throws DataAccessException
	 *             Thrown if a problem occurs while communicating with the
	 *             database.
	 */
	public boolean doesAirportExist(String destinationAirportCode) throws DataAccessException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("select code from airport where code = ?");
			stmt.setString(1, destinationAirportCode.toUpperCase());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("select id from AIRPLANE where id = ?");
			stmt.setInt(1, airplaneId);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("select id from flight where id = ?");
			stmt.setInt(1, flightId);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement("select available_seats from flight where id = ?");
			stmt.setInt(1, flightId);

			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
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
		Collection<Flight> flights = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String query = generateSearchQuery(searchFilters);
			stmt = conn.prepareStatement(query);

			if (searchFilters.getDateOfTrip() != null) {
				stmt.setDate(1, new Date(searchFilters.getDateOfTrip().getTime()));
			} else {
				stmt.setDate(1, new Date(new java.util.Date().getTime()));
			}

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				if (flights == null) {
					flights = new ArrayList<Flight>();
				}

				Flight flight = new Flight();
				flight.setId(rs.getInt(1));
				flight.setDepartureDate(rs.getDate(2));
				flight.setDepartureAirportCode(rs.getString(3));
				flight.setDestinationAirportCode(rs.getString(4));
				flight.setCost(rs.getDouble(5));
				flight.setAirplaneId(rs.getInt(6));
				flight.setAvailableSeats(rs.getInt(7));

				flights.add(flight);
			}

			return flights;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

	/**
	 * Utility method used to create the search string based on the filters
	 * provided to run a search for flights on.
	 * 
	 * @param searchFilters
	 *            Filters to use when searching for flights.
	 * @return Query string to use to search for flights.
	 */
	private String generateSearchQuery(SearchFilters searchFilters) {
		String query = "select * from FLIGHT ";
		String clause = "";

		if (searchFilters.getDateOfTrip() != null) {
			clause += " WHERE DEPARTURE_DATE = ?";
		} else {
			/*
			 * If no date is provided, we still want to limit the results
			 * because we don't want to return flights that have already
			 * occurred.
			 */
			clause += " WHERE DEPARTURE_DATE >= ?";
		}

		if (searchFilters.getDepartureLocation() != null && !searchFilters.getDepartureLocation().trim().equals("")) {
			clause += " AND DEPARTURE_AIRPORT_CODE = '" + searchFilters.getDepartureLocation() + "'";
		}
		if (searchFilters.getDestinationLocation() != null && !searchFilters.getDestinationLocation().trim().equals("")) {
			clause += " AND DESTINATION_AIRPORT_CODE = '" + searchFilters.getDestinationLocation() + "'";
		}

		return query + clause + " ORDER BY DEPARTURE_AIRPORT_CODE, DESTINATION_AIRPORT_CODE ASC";
	}

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
}
