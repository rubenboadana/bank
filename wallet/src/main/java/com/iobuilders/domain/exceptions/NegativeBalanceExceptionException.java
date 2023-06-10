package com.iobuilders.domain.exceptions;

public class NegativeBalanceExceptionException extends BadRequestException {
    private static final String BASE_TEXT = "Balance is going to be negative for the wallet with id : ";

    public NegativeBalanceExceptionException(String message) {
        super(BASE_TEXT + message);
    }
}
