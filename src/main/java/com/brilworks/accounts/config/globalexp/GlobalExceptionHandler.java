package com.brilworks.accounts.config.globalexp;


import com.brilworks.accounts.controller.AuthValidator;
import com.brilworks.accounts.exception.BaseException;
import com.brilworks.accounts.exception.NotFoundException;
import com.brilworks.accounts.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	AuthValidator authValidator;


	/**
	 * initBinder is used by validator for add validation for class like Date
	 * etc.
	 *
	 * @param binder
	 *            used for register new validator
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
        //nosonar
	}

    private static String shortenedStackTrace(Exception e, int maxLines) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        String[] lines = writer.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(lines.length, maxLines); i++) {
            sb.append(lines[i]).append("\n");
        }
        return sb.toString();
    }

	@ExceptionHandler(BaseException.class)
	@ResponseBody
    public ErrorDto webApplicationException(BaseException baseException, HttpServletResponse response, Authentication auth) {;
		log.debug(shortenedStackTrace(baseException, 3));
        response.setStatus(baseException.getStatus());
            ErrorDto dtp =  new ErrorDto(baseException.getErrorMessage(), baseException.getErrorCode(),
                    baseException.getDeveloperMessage());
            return dtp;

    }


	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ErrorDto webApplicationException(NotFoundException baseException, HttpServletResponse response, Authentication auth) {;
		log.debug(shortenedStackTrace(baseException, 3));
		response.setStatus(baseException.getStatus());
		ErrorDto dtp =  new ErrorDto(baseException.getErrorMessage(), baseException.getErrorCode(),
				baseException.getDeveloperMessage());
		return dtp;

	}

//    @ExceptionHandler(FieldErrorException.class)
//	@ResponseBody
//	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	public Map<String, Object> fieldErrorException(FieldErrorException fieldErrorException) {
//		log.error("fieldErrorException", fieldErrorException);
//		Map<String, Object> responseData = null;
//		responseData = new HashMap<String, Object>();
//		responseData.put(Constants.ERRORS, fieldErrorException.getValidationErrorDTO().getFieldErrors());
//
//		return responseData;
//	}


	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
	public Map<String, Object> handleMultipartException(MaxUploadSizeExceededException ex) {
		log.error("MaxUploadSizeExceededException", ex);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put(Constants.ERRORS, "Maximum upload size exceeded.");
		return responseData;
	}

	private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDTO dto = new ValidationErrorDTO();
		for (FieldError fieldError : fieldErrors) {
			dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return dto;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, Object> processValidationError(MethodArgumentNotValidException ex) {
		log.debug("processValidationError", ex);
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		Map<String, Object> responseData = null;
		responseData = new HashMap<String, Object>();
		responseData.put(Constants.ERRORS, processFieldErrors(fieldErrors).getFieldErrors());
		//Sentry.capture(ex); //nosonar
		return responseData;
	}

//	@ExceptionHandler(value = { InternalServerErrorException.class })
//	@ResponseBody
//	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
//	public Map<String, Object> internalServerErrorException(InternalServerErrorException exception) {
//		log.error("internalServerErrorException", exception);
//		Map<String, Object> responseData = new HashMap<String, Object>();
//		responseData.put(Constants.ERROR, exception.getMessage());
//		log.error(exception.getMessage());
//		log.info(exception.getMessage());
//		exception.printStackTrace();
//		//Sentry.capture(exception); //nosonar
//		return responseData;
//	}

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public Map<String, Object> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		log.debug("HttpRequestMethodNotSupportedException", exception);
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(Constants.ERROR, exception.getMessage());
		exception.printStackTrace();
		return responseData;
	}

	@ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public Map<String, Object> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
		log.debug("httpMediaTypeNotSupportedException", exception);
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(Constants.ERROR, exception.getMessage());
		log.error(exception.getMessage());
		log.info(exception.getMessage());
		exception.printStackTrace();
		return responseData;
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public Map<String, Object> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
		log.debug("httpMessageNotReadableException", exception);
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(Constants.ERROR, "Not Valid Json Format");
		log.error(exception.getMessage());
		log.info(exception.getMessage());
		exception.printStackTrace();
		//Sentry.capture(exception); //nosonar
		return responseData;
	}

	@ExceptionHandler(value = { IOException.class, InvocationTargetException.class, NoSuchMethodException.class,
			Exception.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> exceptionHandle(Exception exception) {
		log.debug("exceptionHandle", exception);
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put("message", exception.getMessage());
		log.info(exception.getMessage());
		exception.printStackTrace();
		return responseData;
	}

}
