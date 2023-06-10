package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletTransaction;

public interface WalletService {

    void create(Wallet wallet) throws InterruptedException;

    void deposit(WalletTransaction transaction);

    void transfer(WalletTransaction transaction);

}
