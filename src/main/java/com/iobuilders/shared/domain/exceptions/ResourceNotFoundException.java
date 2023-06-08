package com.iobuilders.shared.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final String BASE_TEXT = "Could not find the requested resource: ";

    public ResourceNotFoundException(Long id) {
        super(BASE_TEXT + id);
    }

    public ResourceNotFoundException(String username) {
        super(BASE_TEXT + username);
    }
}
