package com.iobuilders.domain;

import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.enums.TransactionTypes;

public class WalletTransactionObjectMother {

    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120003";
    public static final String DEFAULT_WALLET_OWNER_USERNAME = "rubenboada";

    public static final double DEFAULT_WALLET_QUANTITY = 10;

    public static WalletTransaction deposit() {
        return WalletTransaction.builder()
                .type(TransactionTypes.DEPOSIT)
                .destinyWalletId(DEFAULT_WALLET_ID)
                .createdBy(DEFAULT_WALLET_OWNER_USERNAME)
                .quantity(DEFAULT_WALLET_QUANTITY)
                .build();
    }

}
