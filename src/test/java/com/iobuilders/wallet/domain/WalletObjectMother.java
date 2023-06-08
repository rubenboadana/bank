package com.iobuilders.wallet.domain;

import com.iobuilders.wallet.domain.dto.Quantity;
import com.iobuilders.wallet.domain.dto.WalletDTO;

public class WalletObjectMother {

    public static final int DEFAULT_WALLET_QUANTITY = 10;

    public static WalletDTO basic() {
        return WalletDTO.builder()
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();
    }


}
