package gov.hud.lrs.common.exception;

import java.util.List;

@SuppressWarnings("serial")
public class ConflictException extends ClientException {

	public ConflictException() {
	}

	public ConflictException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(Throwable cause) {
		super(cause);
	}

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}

}
