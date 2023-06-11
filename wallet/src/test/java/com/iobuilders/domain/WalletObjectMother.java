package com.iobuilders.domain;

import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOwner;

public class WalletObjectMother {

    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120001";
    public static final String DEFAULT_WALLET_OWNER_ID = "26929514-237c-11ed-861d-0242ac120002";

    public static final int DEFAULT_WALLET_QUANTITY = 10;

    public static Wallet basic() {
        return Wallet.builder()
                .id(DEFAULT_WALLET_ID)
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID))
                .quantity(new Quantity(DEFAULT_WALLET_QUANTITY))
                .build();
    }

    public static Wallet withQuantity(double quantity) {
        return Wallet.builder()
                .id(DEFAULT_WALLET_ID)
                .owner(new WalletOwner(DEFAULT_WALLET_OWNER_ID))
                .quantity(new Quantity(quantity))
                .build();

    }


}
