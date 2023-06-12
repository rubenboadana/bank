package com.iobuilders.application;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.bus.event.EventBus;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import com.iobuilders.domain.bus.query.FindWalletOwnerQuery;
import com.iobuilders.domain.bus.query.QueryBus;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletOwnerUsername;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.exceptions.InvalidCredentialsException;
import com.iobuilders.domain.exceptions.NegativeBalanceExceptionException;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;
    private final WalletTransactionRepository transactionsRepository;
    private final EventBus eventBus;
    private final QueryBus queryBus;

    @Autowired
    public WalletServiceImpl(WalletRepository repository, WalletTransactionRepository transactionsRepository, EventBus eventBus, QueryBus queryBus) {
        this.repository = repository;
        this.transactionsRepository = transactionsRepository;
        this.eventBus = eventBus;
        this.queryBus = queryBus;
    }

    private static void checkEnoughBalance(WalletTransaction transaction, Wallet wallet) {
        if (wallet.getQuantity() - transaction.getQuantity() < 0) {
            log.error("WalletServiceImpl:checkEnoughBalance: Balance is negative after the transaction " + transaction);
            throw new NegativeBalanceExceptionException(transaction.getOriginWalletId());
        }
    }

    @Override
    public synchronized void create(Wallet wallet) throws InterruptedException {
        log.info("WalletServiceImpl:create: Starting to create the new wallet " + wallet);
        repository.create(wallet);
        eventBus.publish(new WalletCreatedEvent(wallet.getOwnerUsername(), wallet.getId()));
        log.info("WalletServiceImpl:create: Wallet [" + wallet + "] successfully created");
    }

    @Override
    public synchronized void deposit(WalletTransaction transaction) {
        log.info("WalletServiceImpl:deposit: Starting to do the deposit " + transaction);
        checkWalletExist(transaction.getDestinyWalletId());
        repository.deposit(transaction);
        transactionsRepository.add(transaction);
        log.info("WalletServiceImpl:deposit: Deposit [" + transaction + "] successfully created");
    }

    private void checkWalletExist(String walletId) {
        getIfWalletExist(walletId);
    }

    private Wallet getIfWalletExist(String walletId) {
        return repository.findByWalletId(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
    }

    @Override
    public synchronized void transfer(WalletTransaction transaction) throws ExecutionException, InterruptedException {
        log.info("WalletServiceImpl:transfer: Starting to do the transfer " + transaction);
        checkOriginWalletOwner(transaction);
        Wallet originWallet = getIfWalletExist(transaction.getOriginWalletId());
        checkEnoughBalance(transaction, originWallet);
        checkWalletExist(transaction.getDestinyWalletId());

        repository.transfer(transaction);
        transactionsRepository.add(transaction);
        log.info("WalletServiceImpl:transfer: Transfer [" + transaction + "] successfully created");
    }

    private void checkOriginWalletOwner(WalletTransaction transaction) throws ExecutionException, InterruptedException {
        WalletOwnerUsername username = (WalletOwnerUsername) this.queryBus.get(new FindWalletOwnerQuery(transaction.getOriginWalletId()));

        if (!username.getValue().equals(transaction.getCreatedBy())) {
            throw new InvalidCredentialsException(transaction.getCreatedBy());
        }

    }

    @Override
    public WalletOverview findTransactionsByWalletId(String walletId) {
        Wallet wallet = getIfWalletExist(walletId);
        List<WalletTransaction> transactions = transactionsRepository.findTransactionsByWalletId(walletId);

        return WalletOverview.builder()
                .quantity(wallet.getQuantity())
                .transactions(transactions)
                .build();
    }

}
