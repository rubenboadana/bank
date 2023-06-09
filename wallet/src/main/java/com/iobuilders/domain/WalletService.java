package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;

public interface WalletService {

    WalletID create(Wallet wallet);

    void delete(Long id);

    Wallet update(Long id, Wallet wallet);

}
