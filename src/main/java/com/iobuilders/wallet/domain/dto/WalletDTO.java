package com.iobuilders.wallet.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WalletDTO {

    @JsonIgnore
    private Long id;

    private Quantity quantity;


    public int getQuantity() {
        return quantity.getValue();
    }

}
