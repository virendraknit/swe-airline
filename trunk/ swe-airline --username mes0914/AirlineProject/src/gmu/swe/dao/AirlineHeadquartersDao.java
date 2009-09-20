package gmu.swe.dao;

import gmu.swe.domain.Flight;
import gmu.swe.exception.DataAccessException;
import gmu.swe.util.DbUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AirlineHeadquartersDao {

	public void getSomethingFromDb() throws DataAccessException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("");

			while (rs.next()) {

				System.out.println(rs.getString(0));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}

	}

	public void createAirplane(int numberOfSeats, String airplaneType) throws DataAccessException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn
					.prepareStatement("insert into airplane (num_seats, type) values (?, ?)");
			stmt.setInt(1, numberOfSeats);
			stmt.setString(2, airplaneType);
			
			stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}
	
	public void createAirport(String airportCode) throws DataAccessException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			stmt = conn
					.prepareStatement("insert into airplane (code) values (?)");
			stmt.setString(1, airportCode);
			
			stmt.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}
	
	public void createFlight(Flight flight) throws DataAccessException {
//		insert into flight (DEPARTURE_DATE, DEPARTURE_AIRPORT_CODE, 
//				DESTINATION_AIRPORT_CODE, COST, AIRPLANE_ID, 
//				AVAILABLE_SEATS) values ('2009-10-10', 'BWI', 'IAD', 
//				220.00, 1, (select num_seats from airplane where ID = 1))
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

//			System.out.println(stmt);
			
			stmt.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			closeDbObjects(stmt, conn);
		}
	}

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
