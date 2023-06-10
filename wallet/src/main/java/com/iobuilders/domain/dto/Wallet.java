package com.iobuilders.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Wallet {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
