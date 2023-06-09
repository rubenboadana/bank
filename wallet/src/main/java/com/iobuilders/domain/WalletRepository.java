package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;

public interface WalletRepository {

    WalletID create(Wallet wallet);

    void delete(Long id);

    Wallet update(Long id, Wallet wallet);

    Wallet findById(Long id);

}
