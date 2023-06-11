package com.iobuilders.application.handler;

import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.bus.command.CommandHandler;
import com.iobuilders.domain.command.DepositMoneyInWalletCommand;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.enums.TransactionTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DepositMoneyInWalletCommandHandler implements CommandHandler<DepositMoneyInWalletCommand> {

    private final WalletService walletService;

    @Autowired
    public DepositMoneyInWalletCommandHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    @org.axonframework.commandhandling.CommandHandler
    public void handle(DepositMoneyInWalletCommand command) throws InterruptedException {
        log.info("DepositMoneyInWalletCommandHandler:handle: Command received");
        WalletTransaction transaction = WalletTransaction.builder()
                .createdBy(command.getUser())
                .type(TransactionTypes.DEPOSIT)
                .destinyWalletId(command.getWalletId())
                .quantity(command.getQuantity())
                .build();
        walletService.deposit(transaction);
        log.info("DepositMoneyInWalletCommandHandler:handle: Command processed");
    }
}
