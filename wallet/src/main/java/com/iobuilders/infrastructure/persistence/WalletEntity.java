package com.iobuilders.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "wallets")
@Table(name = "wallets")
public class WalletEntity implements Serializable {
    @Id
    private String id;

    @Column
    private double quantity;

    @Column
    private String owner;

}
