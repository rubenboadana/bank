package com.iobuilders.domain.exceptions;

public class ResourceConflictException extends RuntimeException {
    private static final String BASE_TEXT = "There is a conflict with the resource: ";

    public ResourceConflictException(Long id) {
        super(BASE_TEXT + id);
    }

    public ResourceConflictException(String username) {
        super(BASE_TEXT + username);
    }
}
