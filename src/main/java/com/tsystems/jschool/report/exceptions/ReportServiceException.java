package com.tsystems.jschool.report.exceptions;

public class ReportServiceException extends RuntimeException {

    public ReportServiceException(String message) {
        super(message);
    }

    public ReportServiceException(String message, Exception e) {
        super(message, e);
    }
}
