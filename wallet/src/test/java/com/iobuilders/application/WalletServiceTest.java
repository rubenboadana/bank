package com.iobuilders.application;

import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletTransactionObjectMother;
import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.bus.event.EventBus;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.exceptions.NegativeBalanceExceptionException;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class WalletServiceTest {

    private static final String WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final double NEW_WALLET_QUANTITY = 120l;
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
    void should_throwException_when_depositToUnexistingWallet() {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.deposit();
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());

        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.deposit(walletTransaction));
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

    @Test
    void should_throwException_when_transferFromUnexistingWallet() {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transference();
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());

        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_throwException_when_transferFromWalletWithoutEnoughBalance() {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY + 1);
        Wallet originWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());

        //When/Then
        assertThrows(NegativeBalanceExceptionException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_throwException_when_transferToUnexistingWallet() {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY);
        Wallet originWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());


        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_succeed_when_transferHaveNoExceptions() {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY);
        Wallet originWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        Wallet destinyWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());
        doReturn(Optional.of(destinyWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());


        //When
        sut.transfer(walletTransaction);

        //Then
        verify(walletRepositoryMock, times(1)).transfer(walletTransaction);
        verify(walletTransactionRepository, times(1)).add(walletTransaction);
    }


    @Test
    void should_throwException_when_getTransfersFromUnexistingWallet() {
        //Given
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(WALLET_ID);

        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.findTransactionsByWalletId(WALLET_ID));
    }

    @Test
    void should_succeed_when_getTransfersFromExistingWallet() {
        //Given
        Wallet originWallet = WalletObjectMother.basic();
        WalletTransaction deposit = WalletTransactionObjectMother.deposit();
        WalletTransaction transference = WalletTransactionObjectMother.transference();
        List<WalletTransaction> expectedTransactions = List.of(deposit, transference);

        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(WALLET_ID);
        doReturn(expectedTransactions).when(walletTransactionRepository).findTransactionsByWalletId(WALLET_ID);

        //When
        WalletOverview walletOverview = sut.findTransactionsByWalletId(WALLET_ID);

        //Then
        assertEquals(originWallet.getQuantity() + deposit.getQuantity() - transference.getQuantity(), walletOverview.getQuantity());
        assertEquals(expectedTransactions, walletOverview.getTransactions());
    }


}
