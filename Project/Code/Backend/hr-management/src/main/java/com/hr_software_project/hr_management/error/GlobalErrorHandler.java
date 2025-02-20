package com.hr_software_project.hr_management.error;

import com.hr_software_project.hr_management.response.ResponseStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.hr_software_project.hr_management.response.*;
@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);



	@ExceptionHandler(ServiceException.class)//ServiceResponse
	public ResponseEntity<Object> handleCoreException(ServiceException e) {
		final ErrorCase errorCase = e.getErrorCase();
		if(e!=null && errorCase.getErrorCode()==201 && errorCase.getErrorMessage().equalsIgnoreCase("Empty list")) {

			return buildResponseEntity(new ErrorResponse(ResponseStatusEnum.CREATED).fromErrorCase(errorCase));

		}

		return buildResponseEntity(new ErrorResponse(ResponseStatusEnum.EXPECTED_ERROR).fromErrorCase(errorCase));

	}
	private ResponseEntity<Object> buildResponseEntity(ErrorResponse apiError) {

		if(apiError!=null && apiError.getErrorCode()==201 && apiError.getErrorMessage().equalsIgnoreCase("Empty list")) {

			 return new ResponseEntity<>(apiError, HttpStatus.CREATED);

		}
	       return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
	   }

//	@ExceptionHandler(Exception.class)
//	public ServiceResponse handleGenericException(final Exception e) {
//		LOGGER.error(e.toString());
//		LOGGER.error("**********1 **********Fatal Exception Stacktrace \n", e);
//
//		return new ErrorResponse(ResponseStatusEnum.GENERIC_ERROR).fromErrorCase(ServiceErrorCodes.SYSTEM_ERROR);
//	}


	@ExceptionHandler(RuntimeException.class)
	public ServiceResponse handleRuntimeException(final RuntimeException e) {
		LOGGER.error(e.toString());
		LOGGER.error("**********2 **********Fatal Exception Stacktrace \n", e);

		return new ErrorResponse(ResponseStatusEnum.GENERIC_ERROR).fromErrorCase(ServiceErrorCodes.SYSTEM_ERROR);

	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Object> handleDataNotFoundException(final DataNotFoundException e) {
		final ErrorCase errorCase = e.getErrorCase();

		return buildResponseEntity(new ErrorResponse(ResponseStatusEnum.EXPECTED_ERROR, "", HttpStatus.NOT_FOUND).fromErrorCase(errorCase));

	}


	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
        LOGGER.error(ex.toString());
		LOGGER.error("**********3 **********Internal Error \n", ex);
		final ErrorResponse response =new ErrorResponse(ResponseStatusEnum.GENERIC_ERROR,ServiceErrorCodes.SYSTEM_ERROR.getErrorMessage(),HttpStatus.OK);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}



}
