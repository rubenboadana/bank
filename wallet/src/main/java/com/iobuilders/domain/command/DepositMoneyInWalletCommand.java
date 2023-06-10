package com.iobuilders.domain.command;

import com.iobuilders.domain.bus.command.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DepositMoneyInWalletCommand implements Command {
    private final String walletId;
    private final String user;
    private final Double quantity;
}
