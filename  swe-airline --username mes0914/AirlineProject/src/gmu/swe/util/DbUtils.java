package gmu.swe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		String dbPath = ResourceBundleUtils.getProperty("DB_FILE_PATH");
		
		String dbConnectionString = "jdbc:hsqldb:file:" + dbPath + "/AIRLINE_DB";

		// connect to the database. This will load the db files and start the
		// database if it is not alread running.
		// db_file_name_prefix is used to open or create files that hold the
		// state
		// of the db.
		// It can contain directory names relative to the
		// current working directory
		Connection connection = DriverManager.getConnection(dbConnectionString, "sa", "");
		connection.setAutoCommit(true);
		
		return connection;
	}
	
	public static Connection getConnection(String dbPath) throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		
		String dbConnectionString = "jdbc:hsqldb:file:" + dbPath + "/AIRLINE_DB";
		return DriverManager.getConnection(dbConnectionString, "sa", "");
	}
}
