package com.iobuilders.application;

import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.WalletTransactionObjectMother;
import com.iobuilders.domain.WalletTransactionRepository;
import com.iobuilders.domain.bus.event.EventBus;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import com.iobuilders.domain.bus.query.FindWalletOwnerQuery;
import com.iobuilders.domain.bus.query.QueryBus;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.dto.WalletOwnerUsername;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.exceptions.InvalidCredentialsException;
import com.iobuilders.domain.exceptions.NegativeBalanceExceptionException;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class WalletServiceTest {

    private static final String WALLET_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final String ALIEN_USERNAME = "rubenboada22222";
    private static final double NEW_WALLET_QUANTITY = 120l;
    public static final String USERNAME = "rubenboada";
    public static final int PAGE = 0;
    public static final int NUM_OF_ELEMENTS = 10;
    @Mock
    private WalletRepository walletRepositoryMock;

    @Mock
    private WalletTransactionRepository walletTransactionRepository;
    @Mock
    private EventBus eventBus;

    @Mock
    private QueryBus queryBus;

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
    void should_throwException_when_depositToUnexistingWallet() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.deposit();
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());

        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.deposit(walletTransaction));
    }

    @Test
    void should_throwException_when_depositToExternalWallet() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.deposit();
        doReturn(Optional.of(WalletObjectMother.basic())).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());
        doReturn(new WalletOwnerUsername(ALIEN_USERNAME)).when(queryBus).get(any(FindWalletOwnerQuery.class));

        //When/Then
        assertThrows(InvalidCredentialsException.class, () -> sut.deposit(walletTransaction));
    }

    @Test
    void should_returnUpdatedWallet_when_updateSucceed() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction transaction = WalletTransactionObjectMother.deposit();
        doReturn(Optional.of(WalletObjectMother.basic())).when(walletRepositoryMock).findByWalletId(transaction.getDestinyWalletId());
        doReturn(new WalletOwnerUsername(transaction.getCreatedBy())).when(queryBus).get(any(FindWalletOwnerQuery.class));

        //When
        sut.deposit(transaction);

        //Then
        verify(walletRepositoryMock, times(1)).deposit(transaction);
        verify(walletTransactionRepository, times(1)).add(transaction);
    }

    @Test
    void should_throwException_when_transferFromUnexistingWallet() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transference();
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());
        doReturn(new WalletOwnerUsername(walletTransaction.getCreatedBy())).when(queryBus).get(any(FindWalletOwnerQuery.class));

        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_throwException_when_transferFromWalletWithoutEnoughBalance() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY + 1);
        Wallet originWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());
        doReturn(new WalletOwnerUsername(walletTransaction.getCreatedBy())).when(queryBus).get(any(FindWalletOwnerQuery.class));

        //When/Then
        assertThrows(NegativeBalanceExceptionException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_throwException_when_transferFromAlienWallet() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY);
        doReturn(new WalletOwnerUsername(ALIEN_USERNAME)).when(queryBus).get(any(FindWalletOwnerQuery.class));


        //When/Then
        assertThrows(InvalidCredentialsException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_throwException_when_transferToUnexistingWallet() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY);
        Wallet originWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());
        doReturn(new WalletOwnerUsername(walletTransaction.getCreatedBy())).when(queryBus).get(any(FindWalletOwnerQuery.class));


        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.transfer(walletTransaction));
    }

    @Test
    void should_succeed_when_transferHaveNoExceptions() throws ExecutionException, InterruptedException {
        //Given
        WalletTransaction walletTransaction = WalletTransactionObjectMother.transferenceWithQuantity(NEW_WALLET_QUANTITY);
        Wallet originWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        Wallet destinyWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getOriginWalletId());
        doReturn(Optional.of(destinyWallet)).when(walletRepositoryMock).findByWalletId(walletTransaction.getDestinyWalletId());
        doReturn(new WalletOwnerUsername(walletTransaction.getCreatedBy())).when(queryBus).get(any(FindWalletOwnerQuery.class));

        //When
        sut.transfer(walletTransaction);

        //Then
        verify(walletRepositoryMock, times(1)).transfer(walletTransaction);
        verify(walletTransactionRepository, times(1)).add(walletTransaction);
    }


    @Test
    void should_throwException_when_getTransfersFromUnexistingWallet() throws ExecutionException, InterruptedException {
        //Given
        Pageable pageable = PageRequest.of(0, 10);
        doReturn(Optional.empty()).when(walletRepositoryMock).findByWalletId(WALLET_ID);
        doReturn(new WalletOwnerUsername(USERNAME)).when(queryBus).get(any(FindWalletOwnerQuery.class));


        //When/Then
        assertThrows(WalletNotFoundException.class, () -> sut.findTransactionsByWalletId(USERNAME, WALLET_ID, pageable));
    }

    @Test
    void should_succeed_when_getTransfersFromExistingWallet() throws ExecutionException, InterruptedException {
        //Given
        Wallet originWallet = WalletObjectMother.basic();
        WalletTransaction deposit = WalletTransactionObjectMother.deposit();
        WalletTransaction transference = WalletTransactionObjectMother.transference();
        List<WalletTransaction> expectedTransactions = List.of(deposit, transference);
        Pageable pageable = PageRequest.of(PAGE, NUM_OF_ELEMENTS);

        doReturn(Optional.of(originWallet)).when(walletRepositoryMock).findByWalletId(WALLET_ID);
        doReturn(expectedTransactions).when(walletTransactionRepository).findTransactionsByWalletId(WALLET_ID, pageable);
        doReturn(new WalletOwnerUsername(originWallet.getOwnerUsername())).when(queryBus).get(any(FindWalletOwnerQuery.class));

        //When
        WalletOverview walletOverview = sut.findTransactionsByWalletId(originWallet.getOwnerUsername(), WALLET_ID, pageable);

        //Then
        assertEquals(originWallet.getQuantity() + deposit.getQuantity() - transference.getQuantity(), walletOverview.getQuantity());
        assertEquals(expectedTransactions, walletOverview.getTransactions());
    }


}
