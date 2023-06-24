package com.brilworks.accounts.exception;

public class AuthorizationException extends BaseException {

	private static final long serialVersionUID = 1119066419517920757L;

	public AuthorizationException() {
		super(401, "4010101", "Not authorized", "Not authorized");
	}

	public AuthorizationException(String applicationMessage) {
		super(401, "4010101", "Not authorized", applicationMessage);
	}

	public AuthorizationException(String errorMessage, String applicationMessage) {
		super(401, "4010102", errorMessage, applicationMessage);
	}

}
