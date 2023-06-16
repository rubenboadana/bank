package com.iobuilders.domain;

import com.iobuilders.domain.dto.WalletTransaction;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletTransactionRepository {

    void add(WalletTransaction transaction);

    List<WalletTransaction> findTransactionsByWalletId(String walletId, Pageable pageable);

}
