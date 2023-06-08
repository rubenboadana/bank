package com.iobuilders.wallet.domain.exceptions;

public class WalletNotFoundException extends RuntimeException {
    private static final String BASE_TEXT = "Could not find wallet with id ";

    public WalletNotFoundException(Long id) {
        super(BASE_TEXT + id);
    }
}
