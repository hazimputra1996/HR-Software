package com.hr_software_project.hr_management.response;

import lombok.Data;

@Data
public class Status {

	private int statusCode;
	private String statusMessage;

	public Status() {
		this(ResponseStatusEnum.SUCCESS);
	}

	public Status(ResponseStatusEnum responseStatus) {
		this(responseStatus.getStatusCode(), responseStatus.getStatusMessage());
	}

	public Status(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
}
