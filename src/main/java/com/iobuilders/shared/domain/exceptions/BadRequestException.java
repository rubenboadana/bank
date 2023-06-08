package com.iobuilders.shared.domain.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String BASE_TEXT = "Could not find the requested resource: ";

    public BadRequestException(Long id) {
        super(BASE_TEXT + id);
    }

    public BadRequestException(String username) {
        super(BASE_TEXT + username);
    }
}
