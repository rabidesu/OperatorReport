package com.tsystems.jschool.report.exceptions;

public class ReportDaoException extends RuntimeException {

    public ReportDaoException(String message) {
        super(message);
    }

    public ReportDaoException(String message, Exception e) {
        super(message, e);
    }
}
