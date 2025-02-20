package com.hr_software_project.hr_management.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hr_software_project.hr_management.error.ErrorCase;
import org.springframework.http.HttpStatus;


@JsonInclude(Include.NON_NULL)
public class ErrorResponse extends ServiceResponse {

	private Integer errorCode;
	private String errorMessage;
	private HttpStatus httpStatus;

	public ErrorResponse() {
		this(ResponseStatusEnum.GENERIC_ERROR);
	}

	public ErrorResponse(ResponseStatusEnum responseStatus, String errorMessage, HttpStatus httpStatus) {
		super(new Status(responseStatus));
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	public ErrorResponse(ResponseStatusEnum responseStatus) {
		super(new Status(responseStatus));
	}

	public ErrorResponse(Status status, Integer errorCode, String errorMessage) {
		super(status);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ErrorResponse fromErrorCase(ErrorCase errorCase) {
		setErrorCode(errorCase.getErrorCode());
		setErrorMessage(errorCase.getErrorMessage());

		return this;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
