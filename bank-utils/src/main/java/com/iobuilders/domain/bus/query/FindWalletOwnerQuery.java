package com.iobuilders.domain.bus.query;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class FindWalletOwnerQuery implements Query {
    private final String walletId;
}
