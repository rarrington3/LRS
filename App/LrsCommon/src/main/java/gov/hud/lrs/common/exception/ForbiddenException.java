package gov.hud.lrs.common.exception;

import java.util.List;

@SuppressWarnings("serial")
public class ForbiddenException extends ClientException {

	public ForbiddenException() {
	}

	public ForbiddenException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

}
