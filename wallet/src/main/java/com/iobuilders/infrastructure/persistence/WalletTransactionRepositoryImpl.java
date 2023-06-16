package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.dto.WalletTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<WalletTransaction> findTransactionsByWalletId(String walletId, Pageable pageable) {
        return walletTransactionJPARepository.findByOriginWalletIdOrDestinyWalletIdOrderByCreatedAtDesc(walletId, walletId, pageable)
                .stream().map(this::getDTOFrom).collect(Collectors.toList());
    }

    private WalletTransactionEntity getEntityFrom(WalletTransaction transaction) {
        return WalletTransactionEntity.builder()
                .createdBy(transaction.getCreatedBy())
                .type(transaction.getType())
                .originWalletId(transaction.getOriginWalletId())
                .destinyWalletId(transaction.getDestinyWalletId())
                .quantity(transaction.getQuantity())
                .createdAt(Instant.now())
                .build();
    }

    private WalletTransaction getDTOFrom(WalletTransactionEntity entity) {
        return WalletTransaction.builder()
                .createdBy(entity.getCreatedBy())
                .type(entity.getType())
                .originWalletId(entity.getOriginWalletId())
                .destinyWalletId(entity.getDestinyWalletId())
                .quantity(entity.getQuantity())
                .at(entity.getCreatedAt())
                .build();
    }

}
