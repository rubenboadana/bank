package com.iobuilders.wallet.domain.dto;

import com.iobuilders.wallet.domain.exceptions.QuantityNotValidException;
import lombok.Getter;

@Getter
public class Quantity {

    private final int value;

    public Quantity(int value) {
        if (value < 0) {
            throw new QuantityNotValidException("Quantity cannot be lower than zero");
        }

        this.value = value;
    }

}
