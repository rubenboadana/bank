package com.iobuilders.domain.dto;

import com.iobuilders.domain.exceptions.InvalidUserIDException;

public record UserID(Long id) {
    private static final String IDENTIFIER_LOWER_THAN_ONE = "User id can't be lower than 1";

    public UserID {
        if (id <= 0) {
            throw new InvalidUserIDException(IDENTIFIER_LOWER_THAN_ONE);
        }
    }
}