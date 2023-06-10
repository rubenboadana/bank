package com.iobuilders.application;

import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
final class WalletServiceTest {

    private static final Long WALLET_ID = 1L;
    private static final int NEW_WALLET_QUANTITY = 120;
    @Mock
    private WalletRepository walletRepositoryMock;
    @InjectMocks
    private WalletServiceImpl sut;

    @Test
    void should_returnWalletId_when_createSucceed() {
        //Given
        Wallet wallet = WalletObjectMother.basic();
        WalletID expectedId = new WalletID(WALLET_ID);
        doReturn(expectedId).when(walletRepositoryMock).create(wallet);

        //When
        WalletID newId = sut.create(wallet);

        //Then
        assertThat(newId).isEqualTo(expectedId);
    }

    @Test
    void should_returnUpdatedWallet_when_updateSucceed() {
        //Given
        Wallet wallet = WalletObjectMother.basic();
        Wallet expectedWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(expectedWallet).when(walletRepositoryMock).update(WALLET_ID, wallet);

        //When
        Wallet receivedWallet = sut.update(WALLET_ID, wallet);

        //Then
        assertThat(expectedWallet.getQuantity()).isEqualTo(NEW_WALLET_QUANTITY);
    }

}
