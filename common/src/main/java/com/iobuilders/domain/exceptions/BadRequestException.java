package com.iobuilders.domain.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String BASE_TEXT = "Bad request: ";

    public BadRequestException(Long id) {
        super(BASE_TEXT + id);
    }

    public BadRequestException(String username) {
        super(BASE_TEXT + username);
    }
}
