package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;

public interface WalletService {

    void create(Wallet wallet) throws InterruptedException;

    Wallet update(String id, Wallet wallet);

}
