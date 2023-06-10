package com.iobuilders.application;

import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletTransactionObjectMother;
import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.bus.event.EventBus;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class WalletServiceTest {

    private static final String WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final int NEW_WALLET_QUANTITY = 120;
    @Mock
    private WalletRepository walletRepositoryMock;

    @Mock
    private WalletTransactionRepository walletTransactionRepository;
    @Mock
    private EventBus eventBus;

    @InjectMocks
    private WalletServiceImpl sut;

    @Test
    void should_succeed_when_createHaveNoExceptions() throws InterruptedException {
        //Given
        Wallet wallet = WalletObjectMother.basic();
        WalletCreatedEvent event = new WalletCreatedEvent(wallet.getOwnerUsername(), wallet.getId());

        //When
        sut.create(wallet);

        //Then
        verify(walletRepositoryMock, times(1)).create(wallet);
        verify(eventBus, times(1)).publish(event);
    }

    @Test
    void should_returnUpdatedWallet_when_updateSucceed() {
        //Given
        WalletTransaction transaction = WalletTransactionObjectMother.deposit();
        doReturn(Optional.of(WalletObjectMother.basic())).when(walletRepositoryMock).findByWalletId(transaction.getDestinyWalletId());

        //When
        sut.deposit(transaction);

        //Then
        verify(walletRepositoryMock, times(1)).deposit(transaction);
        verify(walletTransactionRepository, times(1)).add(transaction);
    }

}
