package gov.hud.lrs.common.exception;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UnauthorizedException extends ClientException {

	public UnauthorizedException() {
	}

	public UnauthorizedException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public UnauthorizedException(String message) {
		super(message);

		List<String> errorMessages = new ArrayList<String>();
		errorMessages.add(message);
		this.errorMessages = errorMessages;
	}

	public UnauthorizedException(Throwable cause) {
		super(cause);
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);

		List<String> errorMessages = new ArrayList<String>();
		errorMessages.add(message);
		this.errorMessages = errorMessages;
	}

}
