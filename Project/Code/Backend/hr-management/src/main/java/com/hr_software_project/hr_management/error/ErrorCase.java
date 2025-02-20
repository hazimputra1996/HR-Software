package com.hr_software_project.hr_management.error;

import java.io.Serializable;

public interface ErrorCase extends Serializable {

	/**
	 * @return The ErrorCode with respect to error case
	 */
	int getErrorCode();

	/**
	 * @return Error message associated with the error case
	 */
	String getErrorMessage();
	

}
