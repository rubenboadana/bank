package com.iobuilders.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iobuilders.wallet.domain.exceptions.InvalidWalletIDException;

public class WalletID {

    private static final String IDENTIFIER_LOWER_THAN_ONE = "Wallet id can't be lower than 1";

    @JsonProperty("id")
    private final Long value;

    public WalletID(Long value) {
        if (value <= 0) {
            throw new InvalidWalletIDException(IDENTIFIER_LOWER_THAN_ONE);
        }

        this.value = value;
    }
}