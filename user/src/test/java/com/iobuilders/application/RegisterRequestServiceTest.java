package com.iobuilders.application;

import com.iobuilders.domain.User;
import com.iobuilders.domain.UserObjectMother;
import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.exceptions.UserAlreadyExistsException;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class RegisterRequestServiceTest {

    public static final String DEFAULT_WALLET_ID = "26929514-237c-11ed-861d-0242ac120001";
    public static final String USERNAME = "rubenboada";

    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl sut;

    @Test
    void should_returnUserId_when_createSucceed() {
        //Given
        User user = UserObjectMother.basic();

        //When
        sut.create(user);

        //Then
        verify(userRepositoryMock, times(1)).create(user);
    }

    @Test
    void should_throwException_when_UserAlreadyExists() {
        //Given
        User user = UserObjectMother.basic();
        doReturn(Optional.of(user)).when(userRepositoryMock).findByUserName(user.userName());

        //When/Then
        assertThrows(UserAlreadyExistsException.class, () -> sut.create(user));
    }

    @Test
    void should_bindWallet_when_UserAlreadyExists() {
        //Given
        User user = UserObjectMother.basic();
        doReturn(Optional.of(user)).when(userRepositoryMock).findByUserName(user.userName());

        //When
        sut.bindWallet(user.userName(), DEFAULT_WALLET_ID);

        //Then
        verify(userRepositoryMock, times(1)).bindWallet(user, DEFAULT_WALLET_ID);
    }

    @Test
    void should_notbindWallet_when_UserNotExists() {
        //Given
        doReturn(Optional.empty()).when(userRepositoryMock).findByUserName(USERNAME);

        //When/Then
        assertThrows(UserNotFoundException.class, () -> sut.bindWallet(USERNAME, DEFAULT_WALLET_ID));

    }

}
