package com.iobuilders.domain.command;

import com.iobuilders.domain.bus.command.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateWalletCommand implements Command {
    private final String id;
    private final String owner;
    private final Double quantity;
}
