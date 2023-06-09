package com.iobuilders.domain.dto;

import com.iobuilders.domain.exceptions.InvalidWalletIDException;

public record WalletID(Long id) {
    private static final String IDENTIFIER_LOWER_THAN_ONE = "Wallet id can't be lower than 1";

    public WalletID {
        if (id <= 0) {
            throw new InvalidWalletIDException(IDENTIFIER_LOWER_THAN_ONE);
        }
    }
}