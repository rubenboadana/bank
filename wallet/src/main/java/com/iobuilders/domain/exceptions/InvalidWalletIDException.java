package com.iobuilders.domain.exceptions;

public class InvalidWalletIDException extends BadRequestException {
    private static final String BASE_TEXT = "Wallet id is not valid: ";

    public InvalidWalletIDException(String message) {
        super(BASE_TEXT + message);
    }
}
