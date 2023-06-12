package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletTransaction;

import java.util.concurrent.ExecutionException;

public interface WalletService {

    void create(Wallet wallet) throws InterruptedException;

    void deposit(WalletTransaction transaction);

    void transfer(WalletTransaction transaction) throws ExecutionException, InterruptedException;

    WalletOverview findTransactionsByWalletId(String walletId);
}
