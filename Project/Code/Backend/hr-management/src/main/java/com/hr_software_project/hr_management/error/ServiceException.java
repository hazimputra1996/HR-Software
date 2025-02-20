package com.hr_software_project.hr_management.error;


public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 4392260634186295676L;

	private final ErrorCase errorCase;
	


	public ServiceException(ErrorCase errorCase) {
		super(errorCase.getErrorMessage());
		this.errorCase = errorCase;
	}


	public ServiceException(ErrorCase errorCase, Throwable cause) {
		super(errorCase.getErrorMessage(), cause);
		this.errorCase = errorCase;
	}
	public ErrorCase getErrorCase() {
		return errorCase;
	}

}
