package com.iobuilders.wallet.domain;

import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOwner;

public class WalletObjectMother {

    public static final Long DEFAULT_WALLET_OWNER_ID = 1L;

    public static final int DEFAULT_WALLET_QUANTITY = 10;

    public static Wallet basic() {
        return Wallet.builder()
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID))
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();
    }

    public static Wallet withQuantity(int quantity) {
        return Wallet.builder()
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID))
                .quantity(new Quantity(quantity))
                .build();

    }


}
