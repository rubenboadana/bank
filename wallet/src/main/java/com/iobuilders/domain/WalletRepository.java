package com.iobuilders.domain;

import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;

public interface WalletRepository {

    void create(Wallet wallet);

    Wallet deposit(String id, Quantity quantity);

}
