package com.iobuilders.domain.bus.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class WalletCreatedEvent implements Event {
    private final String ownerUsername;
    private final String walletId;
}
