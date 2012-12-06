package me.test.test;

public class TooBigTestSizeException extends RuntimeException {

	private static final long serialVersionUID = -3533653439517061547L;
	
	private final String message;
	
	public TooBigTestSizeException() {
		this.message = "Test size is too big for this test.";
	}

	@Override
	public String getMessage() {
		return message;
	}

	
}
