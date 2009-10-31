/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Utility class to create a connection to the database.
 * 
 */
public class DbUtils {
	/**
	 * Returns a connection to a HSQLDB database. This class will look up the
	 * configured datasource with JDNI name 'msnyderaDS' and return a
	 * Connection.
	 * 
	 * @return Connection to the database.
	 * @throws SQLException
	 *             Thrown if the creating of the connection fails.
	 * @throws NamingException
	 */
	public static Connection getConnection() throws SQLException {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/msnyderaDS");
			return ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}
}
