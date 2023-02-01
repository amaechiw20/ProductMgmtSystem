package pkg.cogent.exception;

/**
 * 
 * @author: William U. Amaechi
 * @date: 	Jan 18, 2023
 */

public class DateFieldException extends RuntimeException{
	private String errorMessage;
	public DateFieldException() {
		super();
	}
	
	public DateFieldException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}
