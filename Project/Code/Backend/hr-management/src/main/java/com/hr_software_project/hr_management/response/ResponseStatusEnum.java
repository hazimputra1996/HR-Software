package com.hr_software_project.hr_management.response;

public enum ResponseStatusEnum {

	SUCCESS(200, "success"),
	
	CREATED(201, "Created"),
	NO_CONTENT(204, "No Content"),

	ACCEPTED(202, "Accepted"),

	BAD_REQUEST(400, "Bad Request"),

	FORBIDDEN(403, "Forbidden"),

	EXPECTED_ERROR(404, "Not Found"),

	GENERIC_ERROR(500, "Internal Error"),

	UNAUTHORIZED(401, "Unauthorized");

	private int statusCode;
	private String statusMessage;

	private ResponseStatusEnum(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

}
