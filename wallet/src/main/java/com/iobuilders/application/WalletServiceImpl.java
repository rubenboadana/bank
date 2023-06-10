package com.iobuilders.application;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.bus.event.EventBus;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;
    private final EventBus eventBus;

    @Autowired
    public WalletServiceImpl(WalletRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public synchronized void create(Wallet wallet) throws InterruptedException {
        repository.create(wallet);
        eventBus.publish(new WalletCreatedEvent(wallet.getOwnerUsername(), wallet.getId()));
    }

    @Override
    public synchronized Wallet deposit(String walletId, Quantity quantity) {
        return repository.deposit(walletId, quantity);
    }

}
