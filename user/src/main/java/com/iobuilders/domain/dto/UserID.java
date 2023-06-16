package com.iobuilders.domain.dto;

import com.iobuilders.domain.exceptions.InvalidUserIDException;

import java.util.UUID;

public record UserID(String value) {

    public UserID {
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUserIDException(value);
        }
    }
}