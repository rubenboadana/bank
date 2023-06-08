package com.iobuilders.wallet.domain.exceptions;

public class InvalidWalletIDException extends RuntimeException {
    private static final String BASE_TEXT = "Wallet id is not valid: ";

    public InvalidWalletIDException(String message) {
        super(BASE_TEXT + message);
    }
}
