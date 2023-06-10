package com.iobuilders.domain.exceptions;

public class UserAlreadyExistsException extends ResourceConflictException {
    private static final String BASE_TEXT = "User with id ";

    public UserAlreadyExistsException(String username) {
        super(BASE_TEXT + username);
    }
}
