package gmu.swe.ui;

import gmu.swe.exception.DataAccessException;
import gmu.swe.util.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTester {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
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

	@Test
	public void testPrintAirplanes() throws Exception {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection("/Users/mbsnyder/projects/swe645/AirlineProject/database");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from airplane");

			System.out.println("** All Airplanes");

			System.out.println("PLANE ID\tPLANE TYPE\t# SEATS");
			while (rs.next()) {
				String airplanes = rs.getObject(1) + "\t\t" + rs.getObject(3) + "\t\t" + rs.getObject(2);
				// String airplanes = "PlaneId(" + rs.getObject(1) +
				// "), SeatsCount(" + rs.getObject(2) + "), PlaneType("
				// + rs.getObject(3) + ")";
				System.out.println(airplanes);
			}

			System.out.println("");
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

	@Test
	public void testPrintAirports() throws Exception {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection("/Users/mbsnyder/projects/swe645/AirlineProject/database");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from airport");

			System.out.println("** All Airports");

			while (rs.next()) {
				String airplanes = "AirportCode(" + rs.getObject(1) + ")";
				System.out.println(airplanes);
			}

			System.out.println("");
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

	@Test
	public void testPrintFlights() throws Exception {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection("/Users/mbsnyder/projects/swe645/AirlineProject/database");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from flight");

			System.out.println("** All Flights");

			while (rs.next()) {
				String airplanes = "FlightId(" + rs.getObject(1) + "), DepartDate(" + rs.getObject(2)
						+ "), DepartCode(" + rs.getObject(3) + "), DestCode(" + rs.getObject(4) + "), Cost($"
						+ rs.getObject(5) + "), PlaneId(" + rs.getObject(6) + "), AvailableSeats(" + rs.getObject(7)
						+ ")";
				System.out.println(airplanes);
			}

			System.out.println("");
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

	@Test
	public void testPrintReservations() throws Exception {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DbUtils.getConnection("/Users/mbsnyder/projects/swe645/AirlineProject/database");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from reservation");

			System.out.println("** All Reservations");

			while (rs.next()) {
				String airplanes = "ReservationId(" + rs.getObject(1) + "), FlightId(" + rs.getObject(2)
						+ "), NumSeatsReserved(" + rs.getObject(3) + ")";
				System.out.println(airplanes);
			}

			System.out.println("");
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

}
