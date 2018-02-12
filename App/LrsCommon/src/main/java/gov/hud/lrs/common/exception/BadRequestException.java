package gov.hud.lrs.common.exception;

import java.util.List;

@SuppressWarnings("serial")
public class BadRequestException extends ClientException {

	public BadRequestException() {
	}

	public BadRequestException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
