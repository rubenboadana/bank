package com.iobuilders.application;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletID create(Wallet wallet) {
        return repository.create(wallet);
    }

    @Override
    public Wallet update(Long id, Wallet wallet) {
        return repository.update(id, wallet);
    }

}
