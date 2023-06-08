package com.iobuilders.wallet.infrastructure.persistence;

import com.iobuilders.wallet.domain.WalletRepository;
import com.iobuilders.wallet.domain.dto.WalletDTO;
import com.iobuilders.wallet.domain.dto.Quantity;
import com.iobuilders.wallet.domain.dto.WalletID;
import com.iobuilders.wallet.domain.exceptions.WalletNotFoundException;
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
    public WalletID create(WalletDTO wallet) {
        WalletEntity entity = getEntityFrom(wallet);
        WalletEntity response = walletJPARepository.save(entity);

        return new WalletID(response.getId());
    }

    @Override
    public void delete(Long id) {
        walletJPARepository.deleteById(id);
    }

    @Override
    public WalletDTO update(Long id, WalletDTO wallet) {
        WalletEntity oldEntity = walletJPARepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        oldEntity.setQuantity(wallet.getQuantity());

        WalletEntity newEntity = walletJPARepository.save(oldEntity);

        return getDTOFrom(newEntity);
    }

    @Override
    public WalletDTO findById(Long id) {
        WalletEntity walletEntity = walletJPARepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
        return getDTOFrom(walletEntity);
    }
    private WalletEntity getEntityFrom(WalletDTO wallet) {
        return WalletEntity.builder()
                .quantity(wallet.getQuantity())
                .build();
    }

    private WalletDTO getDTOFrom(WalletEntity walletEntity) {
        return new WalletDTO(walletEntity.getId(), new Quantity(walletEntity.getQuantity()));
    }
}
