/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.exception;

import java.util.ArrayList;

/**
 * Custom exception used for validation errors. This class gives the same
 * functionality as {@link Exception}, but also provides a property for holding
 * custom exception. It also has a method to get the custom messages, and to
 * determine if there are any error messages.
 * 
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 8814913258526253236L;

	// Contains the custom messages that are added.
	private ArrayList<String> errorMessages;

	/**
	 * Instantiates the error message property.
	 */
	public ValidationException() {
		this.errorMessages = new ArrayList<String>();
	}

	/**
	 * @see Exception#Exception()
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * @see Exception#Exception(Throwable)
	 */
	public ValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Adds the provided error message to the list of messages already created
	 * for this object.
	 * 
	 * @param errorMessage
	 *            Error message to add.
	 */
	public void addErrorMessage(String errorMessage) {
		if (errorMessage != null) {
			this.errorMessages.add(errorMessage);
		}
	}

	/**
	 * @return The error messages that are associated with this object (an empty
	 *         ArrayList if there are none.)
	 */
	public ArrayList<String> getErrorMessages() {
		return this.errorMessages;
	}

	/**
	 * @return True/False whether or not there are any error messages.
	 */
	public boolean hasErrors() {
		return !this.errorMessages.isEmpty();
	}
}
