package com.iobuilders.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletJPARepository extends JpaRepository<WalletEntity, String> {
}