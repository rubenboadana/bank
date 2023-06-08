package com.iobuilders.common.utils.http.exception;

public class MissingHttpRequestParametersException extends RuntimeException {
    private static final String MESSAGE = "One or more mandatory parameters are missing";

    public MissingHttpRequestParametersException() {
        super(MESSAGE);
    }
}