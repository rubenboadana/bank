package com.iobuilders.user.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String BASE_TEXT = "Could not find user with id ";

    public UserNotFoundException(Long id) {
        super(BASE_TEXT + id);
    }
}
