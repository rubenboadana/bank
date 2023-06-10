package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletTransaction;

import java.util.Optional;

public interface WalletRepository {

    void create(Wallet wallet);

    void deposit(WalletTransaction transaction);

    void transfer(WalletTransaction transaction);

    Optional<Wallet> findByWalletId(String walletId);

}
