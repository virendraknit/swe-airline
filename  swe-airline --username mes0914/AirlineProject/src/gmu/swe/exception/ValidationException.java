package gmu.swe.exception;

import java.util.ArrayList;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 8814913258526253236L;

	private ArrayList<String> errorMessages;
	
	public ValidationException() {
		this.errorMessages = new ArrayList<String>();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public void addErrorMessage(String errorMessage){
		if(errorMessage != null){
			this.errorMessages.add(errorMessage);
		}
	}
	
	public ArrayList<String> getErrorMessages(){
		return this.errorMessages;
	}
	
	public boolean hasErrors(){
		return !this.errorMessages.isEmpty();
	}
}
