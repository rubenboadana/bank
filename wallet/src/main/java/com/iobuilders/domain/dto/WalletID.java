package com.iobuilders.domain.dto;

import com.iobuilders.domain.exceptions.InvalidWalletIDException;

import java.util.UUID;

public record WalletID(String id) {

    public WalletID {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidWalletIDException(id);
        }
    }

}