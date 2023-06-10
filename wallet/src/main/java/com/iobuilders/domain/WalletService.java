package com.iobuilders.domain;

import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;

public interface WalletService {

    void create(Wallet wallet) throws InterruptedException;

    Wallet deposit(String walletId, Quantity quantity);

}
