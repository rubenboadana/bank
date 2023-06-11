package com.iobuilders.application;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.bus.event.EventBus;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.exceptions.NegativeBalanceExceptionException;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;
    private final WalletTransactionRepository transactionsRepository;
    private final EventBus eventBus;

    @Autowired
    public WalletServiceImpl(WalletRepository repository, WalletTransactionRepository transactionsRepository, EventBus eventBus) {
        this.repository = repository;
        this.transactionsRepository = transactionsRepository;
        this.eventBus = eventBus;
    }

    @Override
    public synchronized void create(Wallet wallet) throws InterruptedException {
        repository.create(wallet);
        eventBus.publish(new WalletCreatedEvent(wallet.getOwnerUsername(), wallet.getId()));
    }

    private static void checkEnoughBalance(WalletTransaction transaction, Wallet wallet) {
        if (wallet.getQuantity() - transaction.getQuantity() < 0) {
            throw new NegativeBalanceExceptionException(transaction.getOriginWalletId());
        }
    }

    @Override
    public synchronized void deposit(WalletTransaction transaction) {
        checkWalletExist(transaction.getDestinyWalletId());
        repository.deposit(transaction);
        transactionsRepository.add(transaction);
    }

    private void checkWalletExist(String walletId) {
        getIfWalletExist(walletId);
    }

    private Wallet getIfWalletExist(String walletId) {
        return repository.findByWalletId(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
    }

    @Override
    public synchronized void transfer(WalletTransaction transaction) {
        Wallet originWallet = getIfWalletExist(transaction.getOriginWalletId());
        checkEnoughBalance(transaction, originWallet);
        checkWalletExist(transaction.getDestinyWalletId());

        repository.transfer(transaction);
        transactionsRepository.add(transaction);
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
