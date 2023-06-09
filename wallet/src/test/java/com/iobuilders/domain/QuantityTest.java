package com.iobuilders.wallet.domain;

import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.exceptions.QuantityNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class QuantityTest {

    @Test
    void should_throwException_when_QuantityIsLowerThanZero() {
        assertThrows(QuantityNotValidException.class, () -> new Quantity(-1));
    }
}

