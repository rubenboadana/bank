package com.iobuilders.application;

import com.iobuilders.domain.UserObjectMother;
import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class UserServiceTest {

    private static final String USER_ID = "26929514-237c-11ed-861d-0242ac120002";
    private static final String NEW_USER_NAME = "Rb";
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl sut;

    @Test
    void should_returnUserId_when_createSucceed() {
        //Given
        User user = UserObjectMother.basic();
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
        User user = UserObjectMother.basic();
        User expectedUser = UserObjectMother.withName(NEW_USER_NAME);
        doReturn(expectedUser).when(userRepositoryMock).update(USER_ID, user);

        //When
        User receivedUser = sut.update(USER_ID, user);

        //Then
        assertThat(receivedUser.name()).isEqualTo(NEW_USER_NAME);
    }

}
