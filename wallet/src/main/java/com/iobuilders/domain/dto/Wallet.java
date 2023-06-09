package com.iobuilders.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Wallet {

    @JsonIgnore
    private Long id;

    private WalletOwner owner;

    private Quantity quantity;


    public int getQuantity() {
        return quantity.getValue();
    }

}
