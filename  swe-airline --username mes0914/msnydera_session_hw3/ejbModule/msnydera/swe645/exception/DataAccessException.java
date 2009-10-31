/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.exception;

/**
 * Custom exception used to wrap the database specific exceptions, and cut down
 * on the number of exceptions thrown.
 * 
 */
public class DataAccessException extends Exception {

	private static final long serialVersionUID = 8123757319324423701L;

	public DataAccessException() {
	}

	public DataAccessException(String arg0) {
		super(arg0);
	}

	public DataAccessException(Throwable arg0) {
		super(arg0);
	}

	public DataAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
