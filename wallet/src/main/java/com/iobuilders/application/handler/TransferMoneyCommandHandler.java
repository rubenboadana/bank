package com.iobuilders.application.handler;

import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.bus.command.CommandHandler;
import com.iobuilders.domain.command.TransferMoneyCommand;
import com.iobuilders.domain.dto.WalletTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferMoneyCommandHandler implements CommandHandler<TransferMoneyCommand> {

    private final WalletService walletService;

    @Autowired
    public TransferMoneyCommandHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    @org.axonframework.commandhandling.CommandHandler
    public void handle(TransferMoneyCommand command) throws InterruptedException {
        WalletTransaction transaction = WalletTransaction.builder()
                .createdBy(command.getCreatedBy())
                .type(command.getType())
                .originWalletId(command.getOriginWalletId())
                .destinyWalletId(command.getDestinyWalletId())
                .quantity(command.getQuantity())
                .build();
        walletService.transfer(transaction);
    }
}
