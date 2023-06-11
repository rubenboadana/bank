package com.iobuilders.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iobuilders.domain.enums.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class WalletTransaction {
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private String originWalletId;
    private String destinyWalletId;
    private TransactionTypes type;
    private double quantity;
    private LocalDateTime at;

}
