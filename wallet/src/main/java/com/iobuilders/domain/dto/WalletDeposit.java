package com.iobuilders.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WalletDeposit {

    private Quantity quantity;

    public double getQuantity() {
        return quantity.getValue();
    }


}
