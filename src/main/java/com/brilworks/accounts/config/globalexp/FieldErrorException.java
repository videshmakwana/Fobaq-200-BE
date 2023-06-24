package com.brilworks.accounts.config.globalexp;

public class FieldErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ValidationErrorDTO validationErrorDTO;

	public FieldErrorException(ValidationErrorDTO validationErrorDTO) {
		this.validationErrorDTO = validationErrorDTO;
	}

	public ValidationErrorDTO getValidationErrorDTO() {
		return validationErrorDTO;
	}

	public void setValidationErrorDTO(ValidationErrorDTO validationErrorDTO) {
		this.validationErrorDTO = validationErrorDTO;
	}

}
