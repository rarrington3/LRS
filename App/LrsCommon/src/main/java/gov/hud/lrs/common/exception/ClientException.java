package gov.hud.lrs.common.exception;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ClientException extends RuntimeException {

	protected List<String> errorMessages = new ArrayList<String>();

	public ClientException() {
	}

	public ClientException(String message) {
		super(message);
	}

	public ClientException(Throwable cause) {
		super(cause);
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessagess(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
