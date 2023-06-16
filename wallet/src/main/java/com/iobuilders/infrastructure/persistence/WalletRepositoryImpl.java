package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOwner;
import com.iobuilders.domain.dto.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJPARepository walletJPARepository;

    @Autowired
    public WalletRepositoryImpl(WalletJPARepository walletJPARepository) {
        this.walletJPARepository = walletJPARepository;
    }

    @Override
    public synchronized void create(Wallet wallet) {
        WalletEntity entity = getEntityFrom(wallet);
        walletJPARepository.save(entity);
    }

    @Override
    public synchronized void deposit(WalletTransaction transaction) {
        WalletEntity oldEntity = walletJPARepository.findById(transaction.getDestinyWalletId()).get();
        oldEntity.setQuantity(oldEntity.getQuantity() + transaction.getQuantity());

        walletJPARepository.save(oldEntity);
    }

    @Override
    public Optional<Wallet> findByWalletId(String walletId) {
        Optional<WalletEntity> entity = walletJPARepository.findById(walletId);
        if (entity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(getDTOFrom(entity.get()));
    }

    @Override
    public void transfer(WalletTransaction transaction) {
        WalletEntity originWallet = walletJPARepository.findById(transaction.getOriginWalletId()).get();
        WalletEntity destinyWallet = walletJPARepository.findById(transaction.getDestinyWalletId()).get();

        double transferredAmount = transaction.getQuantity();
        originWallet.setQuantity(originWallet.getQuantity() - transferredAmount);
        destinyWallet.setQuantity(destinyWallet.getQuantity() + transferredAmount);

        walletJPARepository.saveAll(List.of(originWallet, destinyWallet));
    }

    private WalletEntity getEntityFrom(Wallet wallet) {
        return WalletEntity.builder()
                .id(wallet.getId())
                .quantity(wallet.getQuantity())
                .owner(wallet.getOwnerUsername())
                .build();
    }

    private Wallet getDTOFrom(WalletEntity walletEntity) {
        return Wallet.builder()
                .owner(new WalletOwner(walletEntity.getId()))
                .quantity(new Quantity(walletEntity.getQuantity()))
                .build();
    }
}
