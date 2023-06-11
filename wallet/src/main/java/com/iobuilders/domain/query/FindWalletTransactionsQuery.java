package com.iobuilders.domain.query;

import com.iobuilders.domain.bus.query.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FindWalletTransactionsQuery implements Query {
    private final String walletId;
}
