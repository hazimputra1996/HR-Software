package com.hr_software_project.hr_management.response;


import com.hr_software_project.hr_management.error.ErrorCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4392260634186295676L;

	private final ErrorCase errorCase;
	public DataNotFoundException(ErrorCase errorCase) {
		super(errorCase.getErrorMessage());
		this.errorCase = errorCase;
	}


	public DataNotFoundException(ErrorCase errorCase, Throwable cause) {
		super(errorCase.getErrorMessage(), cause);
		this.errorCase = errorCase;
	}

	public ErrorCase getErrorCase() {
		return errorCase;
	}

}
