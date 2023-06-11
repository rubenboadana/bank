package com.iobuilders.domain;

import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletTransaction;

import java.util.List;

public class WalletOverviewObjectMother {

    public static final String DEFAULT_ORIGIN_WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";
    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120003";
    public static final String DEFAULT_WALLET_OWNER_USERNAME = "rubenboada";

    public static final double DEFAULT_WALLET_QUANTITY = 10;

    public static WalletOverview basic() {
        WalletTransaction deposit = WalletTransactionObjectMother.deposit();
        WalletTransaction transference = WalletTransactionObjectMother.transference();

        return WalletOverview.builder()
                .transactions(List.of(deposit, transference))
                .quantity(DEFAULT_WALLET_QUANTITY)
                .build();
    }

}
