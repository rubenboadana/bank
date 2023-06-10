package com.iobuilders.domain.command;

import com.iobuilders.domain.bus.command.Command;
import com.iobuilders.domain.enums.TransactionTypes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TransferMoneyCommand implements Command {
    private final String createdBy;
    private final String originWalletId;
    private final String destinyWalletId;
    private final TransactionTypes type;
    private final double quantity;
}
