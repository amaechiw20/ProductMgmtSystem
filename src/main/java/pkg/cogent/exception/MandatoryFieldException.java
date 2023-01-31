package pkg.cogent.exception;

/**
 * 
 * @author: William U. Amaechi
 * @date: 	Jan 18, 2023
 */

public class MandatoryFieldException extends RuntimeException{
	private String errorMessage;
	public MandatoryFieldException() {
		super();
	}
	
	public MandatoryFieldException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}
