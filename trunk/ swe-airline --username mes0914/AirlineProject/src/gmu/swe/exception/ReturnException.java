package gmu.swe.exception;

public class ReturnException extends Exception {

	private static final long serialVersionUID = -8023699248933421862L;

	public ReturnException() {
	}

	public ReturnException(String arg0) {
		super(arg0);
	}

	public ReturnException(Throwable arg0) {
		super(arg0);
	}

	public ReturnException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
