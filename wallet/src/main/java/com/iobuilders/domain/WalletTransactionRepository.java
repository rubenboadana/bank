package com.iobuilders.domain;

import com.iobuilders.domain.dto.WalletTransaction;

public interface WalletTransactionRepository {

    void add(WalletTransaction transaction);

}
