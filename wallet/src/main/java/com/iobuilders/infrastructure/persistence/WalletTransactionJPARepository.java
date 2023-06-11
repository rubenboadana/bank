package com.iobuilders.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WalletTransactionJPARepository extends JpaRepository<WalletTransactionEntity, Long> {

    List<WalletTransactionEntity> findByOriginWalletIdOrDestinyWalletIdOrderByCreatedAtDesc(String originWalletId, String destinyWalletId);
}