package com.hr_software_project.hr_management.error;

public enum ServiceErrorCodes implements ErrorCase {

    SYSTEM_ERROR(101, ErrorMessages.getCode("system.101")),
    EMAIL_EXISTS(409, ErrorMessages.getCode("email.409")),
    USERNAME_EXISTS(409, ErrorMessages.getCode("username.409")),
    UNAUTHORIZED(401, ErrorMessages.getCode("unauthorized.401")),
    SUPERVISOR_NOT_EXISTS(404, ErrorMessages.getCode("supervisor.404")),
    WRONG_DATE_FORMAT(403, ErrorMessages.getCode("wrong_date.403")),
    WRONG_DATE_RANGE(403, ErrorMessages.getCode("wrong_date_range.403")),
    DATE_NULL(403, ErrorMessages.getCode("date_null.403")),
    USER_NOT_FOUND(404, ErrorMessages.getCode("user.404")),
    PAYROLL_NOT_FOUND(404, ErrorMessages.getCode("payroll.404")),
    DEDUCTION_NOT_FOUND(404, ErrorMessages.getCode("deduction.404")),
    OVERTIME_NOT_FOUND(404, ErrorMessages.getCode("overtime.404")),
    PUBLIC_HOLIDAY_NOT_FOUND(404, ErrorMessages.getCode("public_holiday.404")),
    LEAVE_NOT_FOUND(404, ErrorMessages.getCode("leave.404")),
    ALLOWANCE_NOT_FOUND(404, ErrorMessages.getCode("allowance.404"));

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
