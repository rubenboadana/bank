package com.iobuilders.domain;

import com.iobuilders.domain.dto.UserID;
import com.iobuilders.domain.exceptions.InvalidUserIDException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserIDTest {

    @Test
    void should_throwException_when_idIsEqualOrLowerThanZero() {
        assertThrows(InvalidUserIDException.class, () -> new UserID(0L));
        assertThrows(InvalidUserIDException.class, () -> new UserID(-1L));
    }
}
