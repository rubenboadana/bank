package com.iobuilders.domain;

import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;

public interface WalletService {

    WalletID create(Wallet wallet);

    Wallet update(Long id, Wallet wallet);

}
