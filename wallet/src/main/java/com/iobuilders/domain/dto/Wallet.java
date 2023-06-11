package com.iobuilders.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Wallet {

    private String id;

    @JsonIgnore
    private WalletOwner owner;

    private Quantity quantity;


    public double getQuantity() {
        return quantity.getValue();
    }

    @JsonIgnore
    public String getOwnerUsername() {
        return owner.username();
    }

}
