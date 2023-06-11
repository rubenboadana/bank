package com.iobuilders.domain;

import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.enums.TransactionTypes;

import java.time.Instant;

public class WalletTransactionObjectMother {

    public static final String DEFAULT_ORIGIN_WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";
    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120003";
    public static final String DEFAULT_WALLET_OWNER_USERNAME = "rubenboada";

    public static final double DEFAULT_WALLET_QUANTITY = 10;

    public static WalletTransaction deposit() {
        return WalletTransaction.builder()
                .type(TransactionTypes.DEPOSIT)
                .destinyWalletId(DEFAULT_WALLET_ID)
                .createdBy(DEFAULT_WALLET_OWNER_USERNAME)
                .quantity(DEFAULT_WALLET_QUANTITY)
                .at(Instant.now())
                .build();
    }

    public static WalletTransaction transference() {
        return WalletTransaction.builder()
                .type(TransactionTypes.TRANSFERENCE)
                .originWalletId(DEFAULT_ORIGIN_WALLET_ID)
                .destinyWalletId(DEFAULT_WALLET_ID)
                .createdBy(DEFAULT_WALLET_OWNER_USERNAME)
                .quantity(DEFAULT_WALLET_QUANTITY)
                .at(Instant.now())
                .build();
    }


    public static WalletTransaction transferenceWithQuantity(double quantity) {
        return WalletTransaction.builder()
                .type(TransactionTypes.TRANSFERENCE)
                .originWalletId(DEFAULT_ORIGIN_WALLET_ID)
                .destinyWalletId(DEFAULT_WALLET_ID)
                .createdBy(DEFAULT_WALLET_OWNER_USERNAME)
                .quantity(quantity)
                .at(Instant.now())
                .build();
    }
}
