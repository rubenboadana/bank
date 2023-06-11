package com.iobuilders.domain.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    private static final String BASE_TEXT = "Invalid credentials provided for the user ";

    public InvalidCredentialsException(Long id) {
        super(BASE_TEXT + id);
    }

    public InvalidCredentialsException(String username) {
        super(BASE_TEXT + username);
    }
}
