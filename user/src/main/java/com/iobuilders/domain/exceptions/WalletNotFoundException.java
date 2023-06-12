package com.iobuilders.domain.exceptions;

public class WalletNotFoundException extends ResourceNotFoundException {
    private static final String BASE_TEXT = "Wallet with id ";


    public WalletNotFoundException(String walletId) {
        super(BASE_TEXT + walletId);
    }

}
