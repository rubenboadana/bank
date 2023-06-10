package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;
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
    public WalletID create(Wallet wallet) {
        WalletEntity entity = getEntityFrom(wallet);
        WalletEntity response = walletJPARepository.save(entity);

        return new WalletID(response.getId());
    }

    @Override
    public Wallet update(Long id, Wallet wallet) {
        WalletEntity oldEntity = walletJPARepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        oldEntity.setQuantity(wallet.getQuantity());

        WalletEntity newEntity = walletJPARepository.save(oldEntity);

        return getDTOFrom(newEntity);
    }

    private WalletEntity getEntityFrom(Wallet wallet) {
        return WalletEntity.builder()
                .quantity(wallet.getQuantity())
                .build();
    }

    private Wallet getDTOFrom(WalletEntity walletEntity) {
        return Wallet.builder()
                .owner(new WalletOwner(walletEntity.getId()))
                .quantity(new Quantity(walletEntity.getQuantity()))
                .build();
    }
}
