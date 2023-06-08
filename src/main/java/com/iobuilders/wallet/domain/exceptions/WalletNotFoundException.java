package com.iobuilders.wallet.domain.exceptions;

import com.iobuilders.shared.domain.exceptions.ResourceNotFoundException;

public class WalletNotFoundException extends ResourceNotFoundException {
    private static final String BASE_TEXT = "Wallet with id ";

    public WalletNotFoundException(Long id) {
        super(BASE_TEXT + id);
    }
}
