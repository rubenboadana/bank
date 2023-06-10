package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;

public interface WalletRepository {

    void create(Wallet wallet);

    Wallet update(String id, Wallet wallet);

}
