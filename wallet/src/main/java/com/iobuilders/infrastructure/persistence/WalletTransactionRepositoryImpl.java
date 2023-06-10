package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.dto.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class WalletTransactionRepositoryImpl implements WalletTransactionRepository {

    private final WalletTransactionJPARepository walletTransactionJPARepository;

    @Autowired
    public WalletTransactionRepositoryImpl(WalletTransactionJPARepository walletTransactionJPARepository) {
        this.walletTransactionJPARepository = walletTransactionJPARepository;
    }

    @Override
    public void add(WalletTransaction transaction) {
        WalletTransactionEntity entity = getEntityFrom(transaction);
        walletTransactionJPARepository.save(entity);
    }

    private WalletTransactionEntity getEntityFrom(WalletTransaction transaction) {
        return WalletTransactionEntity.builder()
                .createdBy(transaction.getCreatedBy())
                .type(transaction.getType())
                .originWalletId(transaction.getOriginWalletId())
                .destinyWalletId(transaction.getDestinyWalletId())
                .quantity(transaction.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();
    }


}
