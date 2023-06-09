package com.iobuilders.domain.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    private static final String BASE_TEXT = "User with id ";

    public UserNotFoundException(Long id) {
        super(BASE_TEXT + id);
    }

    public UserNotFoundException(String username) {
        super(BASE_TEXT + username);
    }
}
