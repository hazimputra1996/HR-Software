package com.hr_software_project.hr_management.error;

public enum ServiceErrorCodes implements ErrorCase {

    SYSTEM_ERROR(101, ErrorMessages.getCode("system.101")),
    EMAIL_EXISTS(409, ErrorMessages.getCode("email.409")),
    USERNAME_EXISTS(409, ErrorMessages.getCode("username.409")),
    UNAUTHORIZED(401, ErrorMessages.getCode("unauthorized.401")),
    SUPERVISOR_NOT_EXISTS(404, ErrorMessages.getCode("supervisor.404")),
    WRONG_DATE_FORMAT(403, ErrorMessages.getCode("wrong_date.403")),;

    private int errorCode;
    private String errorMessage;

    ServiceErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        // TODO Auto-generated method stub
        return errorCode;
    }

    public String getErrorMessage() {
        // TODO Auto-generated method stub
        return errorMessage;
    }
}
