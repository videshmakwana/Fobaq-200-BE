package com.brilworks.accounts.config.globalexp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {

	private String errorMessage;
	private String errorCode;
	private String developerMessage;

	public ErrorDto(String errorMessage, String errorCode, String developerMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.developerMessage = developerMessage;
	}
}