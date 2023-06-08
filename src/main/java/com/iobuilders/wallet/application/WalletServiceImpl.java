package com.iobuilders.wallet.application;

import com.iobuilders.wallet.domain.WalletService;
import com.iobuilders.wallet.domain.WalletRepository;
import com.iobuilders.wallet.domain.dto.WalletDTO;
import com.iobuilders.wallet.domain.dto.WalletID;
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
    public WalletID create(WalletDTO wallet) {
        return repository.create(wallet);
    }

    @Override
    public void delete(Long id) {
        checkIfExists(id);
        repository.delete(id);
    }

    @Override
    public WalletDTO update(Long id, WalletDTO wallet) {
        return repository.update(id, wallet);
    }

    private void checkIfExists(Long id) {
        repository.findById(id);
    }
}
