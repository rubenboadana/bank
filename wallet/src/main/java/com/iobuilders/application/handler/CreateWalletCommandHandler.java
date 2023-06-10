package com.iobuilders.application.handler;

import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.bus.command.CommandHandler;
import com.iobuilders.domain.command.CreateWalletCommand;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOwner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateWalletCommandHandler implements CommandHandler<CreateWalletCommand> {

    private final WalletService walletService;

    @Autowired
    public CreateWalletCommandHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    @org.axonframework.commandhandling.CommandHandler
    public void handle(CreateWalletCommand command) throws InterruptedException {
        Wallet wallet = new Wallet(command.getId(), new WalletOwner(command.getOwner()), new Quantity(command.getQuantity()));
        walletService.create(wallet);
    }
}
