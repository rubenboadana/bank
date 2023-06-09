package com.iobuilders.wallet.domain;

import com.iobuilders.wallet.domain.dto.Quantity;
import com.iobuilders.wallet.domain.dto.Wallet;
import com.iobuilders.wallet.domain.dto.WalletOwner;

public class WalletObjectMother {

    public static final Long DEFAULT_WALLET_OWNER_ID = 1L;
    public static final String DEFAULT_WALLET_OWNER_USERNAME = "rubenboada";

    public static final int DEFAULT_WALLET_QUANTITY = 10;

    public static Wallet basic() {
        return Wallet.builder()
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID, DEFAULT_WALLET_OWNER_USERNAME))
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();
    }

    public static Wallet withQuantity(int quantity) {
        return Wallet.builder()
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID, DEFAULT_WALLET_OWNER_USERNAME))
                .quantity(new Quantity(quantity))
                .build();

    }


}
