package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletTransaction;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.ExecutionException;

public interface WalletService {

    void create(Wallet wallet) throws InterruptedException;

    void deposit(WalletTransaction transaction) throws ExecutionException, InterruptedException;

    void transfer(WalletTransaction transaction) throws ExecutionException, InterruptedException;

    WalletOverview findTransactionsByWalletId(String requestedBy, String walletId, Pageable pageable) throws ExecutionException, InterruptedException;
}
