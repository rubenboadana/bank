package com.iobuilders.domain.dto;

import com.iobuilders.domain.exceptions.QuantityNotValidException;
import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Quantity {

    private double value;

    public Quantity(double value) {
        if (value < 0) {
            throw new QuantityNotValidException("Quantity cannot be lower than zero");
        }

        this.value = value;
    }

}
