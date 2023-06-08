package com.iobuilders.user.domain.exceptions;

public class InvalidUserIDException extends RuntimeException {
    private static final String BASE_TEXT = "User id is not valid: ";

    public InvalidUserIDException(String message) {
        super(BASE_TEXT + message);
    }
}
