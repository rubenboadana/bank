package com.iobuilders.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iobuilders.domain.enums.TransactionTypes;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class WalletTransaction {
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private String originWalletId;
    private String destinyWalletId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransactionTypes type;
    private double quantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime at;

}
