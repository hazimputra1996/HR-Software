package com.hr_software_project.hr_management.response;

import lombok.Data;

@Data
public class ServiceResponse {

	private Status status;

	public ServiceResponse(Status status) {
		this.status = status;
	}

	public ServiceResponse() {
		this(new Status());
	}

	public ServiceResponse(ResponseStatusEnum responseStatus) {
		this(new Status(responseStatus));
	}
}
