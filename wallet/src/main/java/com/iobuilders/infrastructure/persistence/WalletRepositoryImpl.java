package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOwner;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJPARepository walletJPARepository;

    @Autowired
    public WalletRepositoryImpl(WalletJPARepository walletJPARepository){
        this.walletJPARepository =  walletJPARepository;
    }

    @Override
    public void create(Wallet wallet) {
        WalletEntity entity = getEntityFrom(wallet);
        walletJPARepository.save(entity);
    }

    @Override
    public Wallet update(String id, Wallet wallet) {
        WalletEntity oldEntity = walletJPARepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        oldEntity.setQuantity(wallet.getQuantity());

        WalletEntity newEntity = walletJPARepository.save(oldEntity);

        return getDTOFrom(newEntity);
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
