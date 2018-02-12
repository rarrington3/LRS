package gov.hud.lrs.common.exception;

import java.util.List;

@SuppressWarnings("serial")
public class NotFoundException extends ClientException {

	public NotFoundException() {
	}

	public NotFoundException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
