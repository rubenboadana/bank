package com.iobuilders.wallet.domain.dto;

import com.iobuilders.wallet.domain.exceptions.InvalidWalletIDException;

public record WalletID(Long id) {
    private static final String IDENTIFIER_LOWER_THAN_ONE = "Wallet id can't be lower than 1";

    public WalletID {
        if (id <= 0) {
            throw new InvalidWalletIDException(IDENTIFIER_LOWER_THAN_ONE);
        }
    }
}