package com.iobuilders.domain.exceptions;

public class QuantityNotValidException extends BadRequestException {
    private static final String BASE_TEXT = "Quantity is not valid: ";

    public QuantityNotValidException(String message) {
        super(BASE_TEXT + message);
    }
}

