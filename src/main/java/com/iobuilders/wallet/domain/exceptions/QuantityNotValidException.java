package com.iobuilders.wallet.domain.exceptions;

public class QuantityNotValidException extends RuntimeException {
    private static final String BASE_TEXT = "Quantity is not valid: ";

    public QuantityNotValidException(String message) {
        super(BASE_TEXT + message);
    }
}

