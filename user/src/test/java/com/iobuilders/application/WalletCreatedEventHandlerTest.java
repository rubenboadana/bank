package com.iobuilders.application;

import com.iobuilders.application.handler.WalletCreatedEventHandler;
import com.iobuilders.domain.UserService;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
final class WalletCreatedEventHandlerTest {

    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120001";
    public static final String USERNAME = "rubenboada";

    @Mock
    private UserService userService;

    @InjectMocks
    private WalletCreatedEventHandler sut;

    @Test
    void should_returnUserId_when_createSucceed() {
        //Given
        WalletCreatedEvent event = new WalletCreatedEvent(USERNAME, DEFAULT_WALLET_ID);

        //When
        sut.on(event);

        //Then
        verify(userService, times(1)).bindWallet(USERNAME, DEFAULT_WALLET_ID);
    }

}
