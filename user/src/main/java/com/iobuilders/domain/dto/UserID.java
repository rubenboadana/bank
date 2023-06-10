package com.iobuilders.domain.dto;

import com.iobuilders.domain.exceptions.InvalidUserIDException;

import java.util.UUID;

public record UserID(String id) {

    public UserID {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUserIDException(ex.getMessage());
        }
    }
}