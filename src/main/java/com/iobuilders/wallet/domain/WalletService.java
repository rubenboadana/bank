package com.iobuilders.wallet.domain;

import com.iobuilders.wallet.domain.dto.Wallet;
import com.iobuilders.wallet.domain.dto.WalletID;

public interface WalletService {

    WalletID create(Wallet wallet);

    void delete(Long id);

    Wallet update(Long id, Wallet wallet);

}
