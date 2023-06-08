package com.iobuilders.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iobuilders.user.domain.exceptions.InvalidUserIDException;

public class UserID {

    private static final String IDENTIFIER_LOWER_THAN_ONE = "User id can't be lower than 1";

    @JsonProperty("id")
    private final Long value;

    public UserID(Long value) {
        if (value <= 0) {
            throw new InvalidUserIDException(IDENTIFIER_LOWER_THAN_ONE);
        }

        this.value = value;
    }
}