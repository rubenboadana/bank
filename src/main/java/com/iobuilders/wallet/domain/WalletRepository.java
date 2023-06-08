package com.iobuilders.wallet.domain;

import com.iobuilders.wallet.domain.dto.WalletDTO;
import com.iobuilders.wallet.domain.dto.WalletID;

public interface WalletRepository {

    WalletID create(WalletDTO wallet);

    void delete(Long id);

    WalletDTO update(Long id, WalletDTO wallet);

    WalletDTO findById(Long id);

}
