package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "wallet_transactions")
@Table(name = "wallet_transactions")
public class WalletTransactionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "original_wallet_id")
    private String originWalletId;

    @Column(name = "destiny_wallet_id")
    private String destinyWalletId;

    @Column(name = "type")
    private TransactionTypes type;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Instant createdAt;

}
