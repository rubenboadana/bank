package com.iobuilders.user.application;

import com.iobuilders.user.domain.UserObjectMother;
import com.iobuilders.user.domain.UserRepository;
import com.iobuilders.user.domain.dto.UserDTO;
import com.iobuilders.user.domain.dto.UserID;
import com.iobuilders.user.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
final class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final String NEW_USER_NAME = "Rb";
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl sut;

    @Test
    void should_returnUserId_when_createSucceed() {
        //Given
        UserDTO user = UserObjectMother.basic();
        UserID expectedId = new UserID(USER_ID);
        doReturn(expectedId).when(userRepositoryMock).create(user);

        //When
        UserID newId = sut.create(user);

        //Then
        assertThat(newId).isEqualTo(expectedId);
    }

    @Test
    void should_throwException_when_UserNotFound() {
        //Given
        doThrow(new UserNotFoundException(USER_ID)).when(userRepositoryMock).findById(USER_ID);

        //When/Then
        assertThrows(UserNotFoundException.class, () -> sut.delete(USER_ID));
    }

    @Test
    void should_succeed_when_deleteIsPossible() {
        //Given
        doReturn(UserObjectMother.basic()).when(userRepositoryMock).findById(USER_ID);

        //When
        sut.delete(USER_ID);

        //Then
        verify(userRepositoryMock, times(1)).delete(USER_ID);
    }

    @Test
    void should_returnUpdatedUser_when_updateSucceed() {
        //Given
        UserDTO user = UserObjectMother.basic();
        UserDTO expectedUser = UserObjectMother.basic();
        expectedUser.setName(NEW_USER_NAME);
        doReturn(expectedUser).when(userRepositoryMock).update(USER_ID, user);

        //When
        UserDTO receivedUser = sut.update(USER_ID, user);

        //Then
        assertThat(receivedUser.getName()).isEqualTo(NEW_USER_NAME);
    }

}
